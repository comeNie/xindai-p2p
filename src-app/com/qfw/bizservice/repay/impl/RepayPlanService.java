package com.qfw.bizservice.repay.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.qfw.common.exception.BizException;
import com.qfw.common.util.DateUtils;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizRepayPlanDetailBO;
import com.qfw.model.vo.repay.RepayPlanDetailVO;

/**
 * 还款计划生成服务
 * @author kindion
 * @since 2014-04-12
 * @version v1.0
 *
 */
public class RepayPlanService{

	List<BizRepayPlanDetailBO> repayPlanDetailList = new ArrayList<BizRepayPlanDetailBO>();
	private RepayPlanDetailVO repayPlanDetailVO;

	/** 本息 */
	private BigDecimal palIntAmt = BigDecimal.ZERO;
	/** 本金 */
	private BigDecimal palAmt = BigDecimal.ZERO;
	/** 利息 */
	private BigDecimal intAmt = BigDecimal.ZERO;
	/** 本金累计 */
	private BigDecimal ttlPalAmt = BigDecimal.ZERO;
	/** 利息累计 */
	private BigDecimal ttlIntAmt = BigDecimal.ZERO;
	/** 贷款余额 */
	private BigDecimal balAmt = BigDecimal.ZERO;
	/** 首期还款日*/
	private Date firstRepayDate = null;
	
	/**放款日期*/
	private Date loanDate = null;
	/**到期日期*/
	private Date loanDueDate = null;
	/**贷款利率*/
	private BigDecimal loanRate = BigDecimal.ZERO;
	/**计划还款日*/
	private Integer repayDateNum = null;
	/**还款周期*/
	private Integer repayCycle = null;
	/**贷款金额*/
	private BigDecimal loanAmt = null;
	/**贷款期限*/
	private Integer loanTerm = null;
	
	
	private int int_i = 1;
	private Date tmp_startDate = null; 
	private Date tmp_endDate = null;
	
	public RepayPlanService(RepayPlanDetailVO repayPlanDetailVO) throws BizException {
		if(repayPlanDetailVO==null) throw new BizException("参数[repayPlanDetailVO]不能为空");
		this.repayPlanDetailVO=repayPlanDetailVO;
		this.loanDate = repayPlanDetailVO.getLoanDate(); // 放款日期
		this.loanDueDate = repayPlanDetailVO.getLoanDueDate(); // 贷款到期日
		this.loanRate = repayPlanDetailVO.getLoanRate();//贷款利率
		if(repayPlanDetailVO.getRepayDateNum()==null){//约定还款日
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(loanDate);
			this.repayDateNum = calendar.get(Calendar.DATE);
		}else{
			this.repayDateNum = repayPlanDetailVO.getRepayDateNum();
		}
		this.repayCycle = repayPlanDetailVO.getRepayCycle();//还款周期
		this.loanAmt = repayPlanDetailVO.getLoanAmt();//贷款金额
		this.loanTerm = repayPlanDetailVO.getLoanTerm();//贷款 期限
		this.tmp_startDate = repayPlanDetailVO.getLoanDate(); // 初始化开始日期为放款日期
	}
	
	
	/**
	 * 等额本金
	 * 计算公式: 每月还款数 = (贷款金额/月份) + (贷款金额 - 上次归还金额数) * 利率
	 * @return List<BizRepayPlanDetailBO> 还款计划明细
	 *            
	 */
	public List<BizRepayPlanDetailBO> debjCalCulate() throws BizException {
		if(!AppConst.REPAY_TYPE_CD_DEBJ.equals(repayPlanDetailVO.getRepayTypeCD())) throw new BizException("还款方式调用错误,本方法为[等额本金]计算服务"); 
		
		BigDecimal period = getPeriodNumber(loanDate,loanDueDate,repayCycle,repayDateNum);// 获取期数
		palAmt = loanAmt.divide(period, AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_HALF_UP);// 每月还款本金
		balAmt = loanAmt;// 贷款余额
		
		while (true) {
			tmp_endDate = this.getRepayDate(tmp_startDate,repayCycle, repayDateNum);// 约定还款日期
			if(int_i == 1){
				firstRepayDate = tmp_endDate;
			}
			if (int_i == period.intValue()) { // 是否最后一期
				tmp_endDate = loanDueDate;
			}
//			intAmt = (balAmt.multiply(loanRate).multiply(new BigDecimal(DateUtils.minuDay(loanDate, loanDueDate))))
//					.divide(AppConst.BASEINTDAY,AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_HALF_UP);// 利息
			intAmt = (balAmt.multiply(loanRate).multiply(new BigDecimal(repayCycle))).divide(new BigDecimal("12"),AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_HALF_UP);// 利息
			palIntAmt = palAmt.add(intAmt);// 本息
			ttlIntAmt = ttlIntAmt.add(intAmt);// 累计利息
			ttlPalAmt = ttlPalAmt.add(palAmt);// 累计本金
			balAmt = balAmt.subtract(palAmt);//本金余额
			if (int_i == period.intValue()) {// 是否最后一期,四舍五入计入尾期
				palIntAmt = palIntAmt.add(balAmt);//本息
				palAmt = palAmt.add(balAmt);//本金
				ttlPalAmt = ttlPalAmt.add(balAmt);//累计本金
				balAmt = BigDecimal.ZERO;//本金余额
			}
			if (int_i > 1) {// 第2期开始的计算日期 = 上期约定还款日期 + 1
				tmp_startDate = DateUtils.addDay(tmp_startDate, 1);
			}
			
			repayPlanDetailList.add(assemRepayPlanBO());
			
			if (tmp_endDate.compareTo(loanDueDate) >= 0) {
				break;
			} else {
				tmp_startDate = tmp_endDate;
				int_i++;
			}
		}
		return repayPlanDetailList;
	}
	
	
	/**
	 * 等额本息
	 * 计算公式: 每月还款数 = (贷款金额 * 利率 * (1 + 利率 ) ^ 期数 )/((1 + 利率) ^ 期数 - 1)
	 * @return List<BizRepayPlanDetailBO> 还款计划明细
	 */
	public List<BizRepayPlanDetailBO> debxCalCulate() throws BizException{
		if(!AppConst.REPAY_TYPE_CD_DEBX.equals(repayPlanDetailVO.getRepayTypeCD())) throw new BizException("还款方式调用错误,本方法为[等额本息]计算服务"); 
		
		int period = getPeriodNumber(loanDate,loanDueDate,repayPlanDetailVO.getRepayCycle(),repayDateNum).intValue();// 获取期数
		int each_days[] = new int[period]; // 期次集合,期次天数
		while (true) {
			tmp_endDate = this.getRepayDate(tmp_startDate,repayCycle, repayDateNum);// 约定还款日期
			if(int_i == 1){
				firstRepayDate = tmp_endDate;
			}
			if (int_i == period) {
				tmp_endDate = loanDueDate;
			}
			each_days[int_i - 1] = (int)DateUtils.minuDay(tmp_startDate, tmp_endDate);
			if (tmp_endDate.compareTo(loanDueDate) >= 0) {
				break;
			} else {
				tmp_startDate = tmp_endDate;
				int_i++;
			}
		}
		/**利用EXECL单变量算法求解*/
		BigDecimal each_sum = BigDecimal.ZERO;// 每期总额，理论上应该是每期都相等，但是因为精度原因，最后一期需要作出牺牲
		BigDecimal[] each_pals = new BigDecimal[period];// 每期本金
		BigDecimal[] each_ints = new BigDecimal[period];// 每期利息
		BigDecimal var_bal =  BigDecimal.ZERO;// 每期本金
		BigDecimal pal_sum =  BigDecimal.ZERO;// 每期本金之和
		boolean forceBreak = false;// 开关
		double inc = 0, jd = 0.0000001, side = -1, x;// 每次调整增量，精度初始值，方向标，贷款总额与本金合计差额
		int count = 0;// 递归次数
		double minDeviation = 1;// 最小误差
		do {
			pal_sum = BigDecimal.ZERO;// 初始化
			var_bal = loanAmt;// 每期本金
			each_sum = each_sum.add(formatDouble(inc));
			for (int i = 0; i < period; i++) {
//				each_ints[i] = var_bal.multiply(loanRate).multiply(new BigDecimal(each_days[i])).divide(AppConst.BASEINTDAY,AppConst.DECIMAL_AMT_PRECISION,BigDecimal.ROUND_HALF_UP);//每期利息
				each_ints[i] = var_bal.multiply(loanRate).multiply(new BigDecimal(repayCycle)).divide(new BigDecimal("12"),AppConst.DECIMAL_AMT_PRECISION,BigDecimal.ROUND_HALF_UP);//每期利息
				each_pals[i] = each_sum.subtract(each_ints[i]);
				var_bal = var_bal.subtract(each_pals[i]);
				pal_sum = pal_sum.add(each_pals[i]);
			}
			x = loanAmt.subtract(pal_sum).doubleValue();
			if (side == -1) {
				side = x > 0 ? 0.1 : -0.1;
			}
			if (x * side > 0) {
				side = -side;
				if (jd == 10) {
					// 如果最小误差两次一样，退出计算
					if (minDeviation == Math.abs(x)) {
						break;
					}
					// 保存最小误差
					if (minDeviation > Math.abs(x)) {
						minDeviation = Math.abs(x);
					}
				} else {
					jd *= 10;
				}
			}
			// 增加量修改
			inc = -side / jd;
		} while (!forceBreak && count++ < 1000 && Math.abs(x) > 0.01);

		tmp_startDate = loanDate;
		balAmt = loanAmt;
		palIntAmt = each_sum; // 本息
		for (int_i = 1; int_i <= period; int_i++) {
			palAmt = each_pals[int_i - 1]; // 本金
			intAmt = each_ints[int_i - 1]; // 利息
			ttlPalAmt = ttlPalAmt.add(palAmt); // 累计本金
			ttlIntAmt = ttlIntAmt.add(intAmt);// 累计利息
			balAmt = balAmt.subtract(palAmt);//贷款余额
			if (int_i == period) {//是否最后一期,四舍五入计入尾期
				palIntAmt = palIntAmt.add(balAmt);//本息
				palAmt = palAmt.add(balAmt);//本金
				ttlPalAmt = ttlPalAmt.add(balAmt);//累计本金
				balAmt = BigDecimal.ZERO;//本金余额
			}
			
			tmp_endDate = DateUtils.addDay(tmp_startDate, each_days[int_i - 1]);
			if (int_i > 1) {// 第2期开始的计算日期 = 上期约定还款日期 + 1
				tmp_startDate = DateUtils.addDay(tmp_startDate, 1);
			}
			repayPlanDetailList.add(assemRepayPlanBO());
			tmp_startDate = tmp_endDate;
			
		}
		return repayPlanDetailList;
	}
	
	/**
	 * 一次性还本付息
	 * 贷款金额 计算公式: 到期一次还本付息额=贷款本金 * [1 + 月利率 * 贷款期(月) ]
	 * @return List<BizRepayPlanDetailBO> 还款计划明细
	 */
	public List<BizRepayPlanDetailBO> lsbqCalCulate() throws BizException{
		if(!AppConst.REPAY_TYPE_CD_LSBQ.equals(repayPlanDetailVO.getRepayTypeCD())) throw new BizException("还款方式调用错误,本方法为[一次性还本付息]计算服务"); 
		
		int dayCnt =(int)DateUtils.minuDay(loanDate, loanDueDate);// 天数
//		intAmt = loanAmt.multiply(loanRate).multiply(new BigDecimal(dayCnt)).divide(AppConst.BASEINTDAY, AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_HALF_UP);// 利息
		intAmt = loanAmt.multiply(loanRate).multiply(new BigDecimal(loanTerm)).divide(new BigDecimal("12"), AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_HALF_UP);// 利息
		palAmt = loanAmt;// 本金
		ttlIntAmt = intAmt;// 累计利息
		ttlPalAmt = palAmt; // 累计本金
		palIntAmt = palAmt.add(intAmt);// 本息
		firstRepayDate = loanDueDate;
		tmp_endDate = loanDueDate;//计划还款日等于融资到期日
		repayPlanDetailList.add(assemRepayPlanBO());
		return repayPlanDetailList;
	}
	
	/**	
	 * 按固定周期付息，到期还本
	 * @return List<BizRepayPlanDetailBO> 还款计划明细
	 */
	public List<BizRepayPlanDetailBO> zqfxCalCulate() throws BizException{
		if(!AppConst.REPAY_TYPE_CD_ZQFX.equals(repayPlanDetailVO.getRepayTypeCD())) throw new BizException("还款方式调用错误,本方法为[按固定周期付息]计算服务"); 
		
		int period = getPeriodNumber(loanDate,loanDueDate,repayCycle,repayDateNum).intValue();// 获取期数
		balAmt = loanAmt; // 贷款余额
		tmp_startDate = loanDate; // 放款日期
		while (true) {
			tmp_endDate = this.getRepayDate(tmp_startDate,repayCycle, repayDateNum);// 约定还款日期
			if(int_i == 1){
				firstRepayDate = tmp_endDate;
			}
			if (int_i == period) { // 是否最后一期
				tmp_endDate = loanDueDate;
			}
//			intAmt = (balAmt.multiply(loanRate).multiply(new BigDecimal(DateUtils.minuDay(tmp_startDate, tmp_endDate)))).divide(AppConst.BASEINTDAY,AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_HALF_UP);// 利息
			intAmt = (balAmt.multiply(loanRate).multiply(new BigDecimal(repayCycle))).divide(new BigDecimal("12"),AppConst.DECIMAL_AMT_PRECISION, BigDecimal.ROUND_HALF_UP);// 利息
			palIntAmt = intAmt;// 本息
			ttlIntAmt = ttlIntAmt.add(intAmt);// 累计利息
			if (int_i == period) { // 是否最后一期 特殊处理最后一期
				palIntAmt = palIntAmt.add(balAmt);
				palAmt = balAmt;
				ttlPalAmt = ttlPalAmt.add(balAmt);
				balAmt = BigDecimal.ZERO;
			}
			if (int_i > 1) {// 第2期开始的计算日期 = 上期约定还款日期 + 1
				tmp_startDate = DateUtils.addDay(tmp_startDate, 1);
			}
			repayPlanDetailList.add(assemRepayPlanBO());
			
			if (tmp_endDate.compareTo(loanDueDate) >= 0) {
				break;
			} else {
				tmp_startDate = tmp_endDate;
				int_i++;
			}
		}
		return repayPlanDetailList;
	}
	
	
	/**
	 * 获取还款计划期数
	 * @param loanStartDate 贷款起始日期
	 * @param loanEndDate 贷款到期日
	 * @param repayCycle 还款周期
	 * @param repayDateNum 约定还款日
	 * @return 还款计划期数(BigDecimal)
	 */
	private BigDecimal getPeriodNumber(Date loanStartDate,Date loanEndDate,Integer repayCycle,Integer repayDateNum) {
		BigDecimal periodNumber = BigDecimal.ONE;// 期次
		Date startDate = loanStartDate; // 放款日期
		while (true) {
			startDate = this.getRepayDate(startDate, repayCycle, repayDateNum);// 约定还款日期
			if (startDate.compareTo(loanEndDate) >= 0 || isMonthCompare(startDate, loanEndDate)) {
				break;
			} else {
				periodNumber = periodNumber.add(BigDecimal.ONE);
			}
		}
		return periodNumber;
	}
	/**
	 * 返回NUM个月的约定还款日期
	 * @param date 初始日期
	 * @param num 周期月数
	 * @param repayDateNum 约定还款日
	 * @return 约定还款日期(Date)
	 */
	private Date getRepayDate(Date date, Integer num, Integer repayDateNum) {
		if (date == null || num == null || repayDateNum == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int cnt = DateUtils.getActualMaximum(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1 + num);
		if (cnt < repayDateNum.intValue()) {
			repayDateNum = cnt;
		}
		date = DateUtils.getDateForString(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1 + num,repayDateNum);
		return date;
	}
	
	/**
	 * 比较年月是否相同
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return true:年月相同 , false:年月不同
	 */
	private boolean isMonthCompare(Date startDate, Date endDate) {
		if (startDate != null && endDate != null) {
			if (DateUtils.getDateString("yyyy-MM",startDate).equals(DateUtils.getDateString("yyyy-MM",endDate))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 组装BizRepayPlanDetailBO对象
	 * @return BizRepayPlanDetailBO
	 */
	private BizRepayPlanDetailBO  assemRepayPlanBO(){
		BizRepayPlanDetailBO repayPlanBO = new BizRepayPlanDetailBO();
		repayPlanBO.setInterestAmt(intAmt);
		repayPlanBO.setPrincipalAmt(palAmt);
		repayPlanBO.setPrincipaInterestAmt(palIntAmt);
		repayPlanBO.setTtlPrincipalAmt(ttlPalAmt);
		repayPlanBO.setTtlInterestAmt(ttlIntAmt);
		repayPlanBO.setLoanRate(loanRate);
		repayPlanBO.setLoanBalAmt(balAmt);
		repayPlanBO.setPeriod(int_i);
		repayPlanBO.setStartInterestDate(tmp_startDate);
		repayPlanBO.setRepayplanDate(tmp_endDate);
		return repayPlanBO; 
	} 
	
	/**
	 * 格式化数字
	 * @param d 
	 */
	private BigDecimal formatDouble(Double d) {
		Format f = new java.text.DecimalFormat("#0.00");
		return new BigDecimal(f.format(d));
	}
	
	/**
	 * 累计本金
	 * @return
	 */
	public BigDecimal getTtlPalAmt() {
		return ttlPalAmt;
	}

	/**
	 * 累计利息
	 * @return
	 */
	public BigDecimal getTtlIntAmt() {
		return ttlIntAmt;
	}
	
	/**
	 * 首期还款日
	 * @return
	 */
	public Date getFirstRepayDate() {
		return firstRepayDate;
	}

	public static void main(String[] args) throws Exception {
		RepayPlanDetailVO repayPlanDetailVO = new RepayPlanDetailVO();
		repayPlanDetailVO.setRepayTypeCD(AppConst.REPAY_TYPE_CD_LSBQ);
		repayPlanDetailVO.setLoanAmt(new BigDecimal("145000"));
		repayPlanDetailVO.setLoanDate(DateUtils.getDateByYMD("2014-03-20"));
		repayPlanDetailVO.setLoanDueDate(DateUtils.addMonth(DateUtils.getDateByYMD("2014-03-20"), 13));
		repayPlanDetailVO.setLoanRate(new BigDecimal("0.0655"));
		repayPlanDetailVO.setRepayDateNum(20);
		repayPlanDetailVO.setRepayCycle(1);
		repayPlanDetailVO.setLoanTerm(13);
		RepayPlanService repayPlanService = new RepayPlanService(repayPlanDetailVO);
		List<BizRepayPlanDetailBO> lists = repayPlanService.lsbqCalCulate();
		for (BizRepayPlanDetailBO bizRepayPlanDetailBO : lists) {
			//System.out.println(bizRepayPlanDetailBO.getPeriod()+" | "+bizRepayPlanDetailBO.getPrincipalAmt()+" | "+bizRepayPlanDetailBO.getInterestAmt()+" | "+bizRepayPlanDetailBO.getPrincipaInterestAmt()+" | "+ bizRepayPlanDetailBO.getLoanBalAmt());
		}
	}
}
