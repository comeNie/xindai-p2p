package com.qfw.test;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qfw.bizservice.batch.IBatchBS;
import com.qfw.bizservice.creditor.ICreditorRightBS;
import com.qfw.bizservice.loan.ILoanBS;
import com.qfw.bizservice.repay.IRepayBS;
import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.StringUtils;
import com.qfw.model.AppConst;
import com.qfw.model.vo.creditor.CreditorRightTranVO;
import com.qfw.model.vo.creditor.CreditorRightVO;
import com.qfw.model.vo.repay.RepayInfoVO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:WebContent/WEB-INF/applicationContext.xml")
public class CoreJunitTest extends AbstractJUnit4SpringContextTests{

	@Autowired
	@Qualifier("repayBS")
	private IRepayBS repayBS;
	@Autowired
	@Qualifier("loanBS")
	private ILoanBS loanBS;
	@Autowired
	@Qualifier("creditorRightBS")
	private ICreditorRightBS creditorRightBS  ;
	@Autowired
	private ISeqBS seqBS;
	@Autowired
	private IBaseDAO baseDAO;
	@Autowired
	private IBaseService baseService;
	@Autowired
	private IBatchBS batchBS;
	
	@org.junit.Test
    public void repayTest() throws BizException{
//		/**准备数据,生成借款申请开始**/
//		String sql = "INSERT INTO biz_loan_apply VALUES (7, '2', '1', 'pmpmpmpm', 10000.00, 12, '1', 'pmpmpmpm', 7, 0.100000, '0', '2014-4-24', '1', 'pmpmpmpm', '0', 1, '2014-4-24 23:57:31', 1, '2014-4-24 23:57:35')";
//		baseService.getJdbcTemplate().execute(sql);
//		/**准备数据,生成借款申请结束**/
//		/**准备数据,生成借款发布开始**/
//		sql = "INSERT INTO biz_loan_approve VALUES (7, 1, '7', 'pmpmpmpm', 'pmpmpmpm', 10000.00, 12, '1', 10000.00, 0, 50.00, 'pmpmpmpm', 7, '2014-4-24 23:59:53', '2014-5-1 23:59:59', 0.100000, '0', 200, 0, 200, '0', NULL, '0', 1, '2014-4-25 00:01:06', 111, '2014-4-25 01:03:24')";
//		baseService.getJdbcTemplate().execute(sql);
//		/**准备数据,生成借款发布结束**/
//		
//		/****投资测试开始****/
//		CreditorRightVO crbo1 = new CreditorRightVO();
//		crbo1.setCrAmt(new BigDecimal("3000"));
//		crbo1.setCustId(2);
//		crbo1.setLoanApproveId(7);
//		
//		CreditorRightVO crbo2 = new CreditorRightVO();
//		crbo2.setCrAmt(new BigDecimal("7000"));
//		crbo2.setCustId(3);
//		crbo2.setLoanApproveId(7);
//		creditorRightBS.submitTrender(crbo1);
//		creditorRightBS.submitTrender(crbo2);
//		/****投资测试结束****/
//		
//		/****借款测试开始****/
//		loanBS.effectLoan(7);
//		/****借款测试结束****/
		
		/****还款测试开始****/
//		repayBS.repayForFull(AppConst.REPAY_WAY_CD_ZC,19,186);
//		repayBS.repayForFull(AppConst.REPAY_WAY_CD_DF,19,103);
//		repayBS.repayForFull(AppConst.REPAY_WAY_CD_TQ,19,null);
		/****还款测试结束****/
		
//		/**测试延期服务*/
//		Date batchDate = DateUtils.getDateByYMD("2014-10-17");
//		batchBS.batchDelayLoan(batchDate);
//		/**测试逾期服务*/
//		batchBS.batchOverdueLoan(batchDate);
		
//		/**测试债权转让发布服务*/
//		CreditorRightTranVO tranVO= new CreditorRightTranVO();
//		tranVO.setCrId(20);
//		tranVO.setTakeAmt(new BigDecimal("100"));
//		tranVO.setTranTtlCount(140);
//		creditorRightBS.creditorTranApprove(tranVO);
//		/**测试债权转让发布服务*/
		
		/**测试债权转让投资服务*/
//		CreditorRightVO crt1 = new CreditorRightVO();
//		crt1.setCrAmt(new BigDecimal("3000"));
//		crt1.setCustId(3);
//		crt1.setCreditorRightTranId(3);
//		creditorRightBS.submitTrenderTran(crt1);
//		
//		CreditorRightVO crt2 = new CreditorRightVO();
//		crt2.setCrAmt(new BigDecimal("4000"));
//		crt2.setCustId(3);
//		crt2.setCreditorRightTranId(3);
//		creditorRightBS.submitTrenderTran(crt2);
		/**测试债权转让投资服务*/
		
		/*测试收益服务*/
//		Date batchDate = DateUtils.getDateByYMD("2015-3-03");
//		batchBS.batchIncomeForRepay(batchDate);
		/*测试收益服务*/
		
		/*测试收益服务*/
//		Date batchDate = DateUtils.getDateByYMD("2015-3-03");
//		batchBS.batchInvalidLoan(batchDate);
		/*测试收益服务*/
		
		/*测试还款试算服务*/
//		RepayInfoVO repayInfoVO = repayBS.repayForTrial(AppConst.REPAY_WAY_CD_ZC,20,121);
//		RepayInfoVO repayInfoVO = repayBS.repayForTrial(AppConst.REPAY_WAY_CD_DF,20,121);
//		RepayInfoVO repayInfoVO = repayBS.repayForTrial(AppConst.REPAY_WAY_CD_TQ,20,null);
		/*测试还款试算服务*/
		
		/*测试还款服务*/
		Date batchDate = DateUtils.getDateByYMD("2014-06-14");
		batchBS.batchRepayForTip(batchDate);
		/*测试还款服务*/
		
	}
}
