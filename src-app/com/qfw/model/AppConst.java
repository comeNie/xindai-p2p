package com.qfw.model;

import java.math.BigDecimal;

import com.qfw.model.bo.BizAutoTenderConfigBO;

public class AppConst {
	//币别:人民币CNY
	public final static String CURRENCY_CD_CNY = "CNY"; //人民币
	
	//用户表有效状态
    public final static String USER_STATUS_YES = "1"; //有效
    //用户表无效状态
    public final static String USER_STATUS_NO = "0"; //无效
	
	public final static int DECIMAL_AMT_PRECISION = 2; //金额小数位
	public final static int DECIMAL_AMT_RATE = 8; //利率小数位
	
	public final static BigDecimal MIN_TENDER_AMT = new BigDecimal("50"); //最小投资金额
	
	//交易编号
	public final static String TXNO = "TXNO"; //交易编号名字
	//充值卡号
	public final static String CARDNUM = "CARDNUM"; //交易编号名字
	
	public final static String BATCHCARDNUM = "BATCHCARDNUM"; //生成批次号
	
	//支付方式:PAYMENT_WAY_CD:自主0\受托1
	public final static String PAYMENT_WAY_CD_SELF = "0";
	public final static String PAYMENT_WAY_CD_TRUSTEE = "1";
	
	//平台帐号类型:会员帐号0\平台收益帐号1\平台还款账户2\平台投资账户3\风险准备金账户4\平台充值账户5\平台提现账户6
	public final static String ACCOUNT_TYPE_CUST = "0";
	public final static String ACCOUNT_TYPE_PMSY = "1";
	public final static String ACCOUNT_TYPE_PMHK = "2";
	public final static String ACCOUNT_TYPE_PMTZ = "3";
	public final static String ACCOUNT_TYPE_PMFXZBJ = "4";
	public final static String ACCOUNT_TYPE_PMCZ="5";
	public final static String ACCOUNT_TYPE_PMTX="6";
	
	
	//CR_TYPE_CD:投标0\转让1
	public final static String CR_TYPE_CD_TENDER = "0";
	public final static String CR_TYPE_CD_TRAN = "1";
	
	//投标方式代码 :自动投标0\手动投标1
	public final static String TENDER_TYPE_CD_AUTO = "0";
	public final static String TENDER_TYPE_CD_HAND = "1";
	
	//借款发布状态APPROVE_STATUS_CD:投标中0\已满标1\撤标2\放款成功3
	public final static String APPROVE_STATUS_CD_TENDERING = "0";
	public final static String APPROVE_STATUS_CD_FULL = "1";
	public final static String APPROVE_STATUS_CD_CANCEL = "2";
	public final static String APPROVE_STATUS_CD_LOAN = "3";//
	
	//债权状态:投标中1\还款中2\逾期3\结清4\撤标5\
	public final static String CR_STATUS_CD_TENDERING = "1";//投标中
	public final static String CR_STATUS_CD_REPAYING = "2";//还款中
	public final static String CR_STATUS_CD_OVERDUE = "3";//逾期中
	public final static String CR_STATUS_CD_END = "4";//结清
	public final static String CR_STATUS_CD_CANCEL = "5";//撤标
	
	//债权转让状态:CRT_STATUS_CD:申请审批中0\审批不通过1\(审批通过)债权转让投标中2\已满标3\撤标4
	public final static String CRT_STATUS_CD_PENDING = "0";//申请审批中0
	public final static String CRT_STATUS_CD_FAILURE = "1";//审批不通过1
	public final static String CRT_STATUS_CD_TENDERING = "2";//(审批通过)债权转让投标中2
	public final static String CRT_STATUS_CD_FULL = "3";//已满标3
	public final static String CRT_STATUS_CD_CANCEL = "4";//撤标4
	
	//还款方式:等额本息0\等额本金1\利随本清2\按固定周期付息,到期还款3
	public final static String REPAY_TYPE_CD_DEBX = "0";//等额本息
	public final static String REPAY_TYPE_CD_DEBJ = "1";//等额本金
	public final static String REPAY_TYPE_CD_LSBQ = "2";//利随本清
	public final static String REPAY_TYPE_CD_ZQFX = "3";//按固定周期付息,到期还款
	
	//贷款基础计息日
	public final static BigDecimal BASEINTDAY = new BigDecimal("360"); //基础计息日
	
	//还款状态:REPAY_STATUS_CD:未还0\已还1\平台垫付2\呆账3
	public final static String REPAY_STATUS_CD_WH = "0";//未还
	public final static String REPAY_STATUS_CD_YH = "1";//已还
	public final static String REPAY_STATUS_CD_PTDF = "2";//平台垫付 
	public final static String REPAY_STATUS_CD_DZ = "3";//呆账 
	
	//欠款标识:ARREARS_FLAG_CD:正常0\宽限期1\展期2\逾期3\呆账4
	public final static String ARREARS_FLAG_CD_ZC = "0";//正常
	public final static String ARREARS_FLAG_CD_KXQ = "1";//宽限期
	public final static String ARREARS_FLAG_CD_ZQ = "2";//展期
	public final static String ARREARS_FLAG_CD_YQ = "3";//逾期
	public final static String ARREARS_FLAG_CD_DZ = "4";//呆账
	
	//欠款状态:ARREARS_STATUS_CD:未还0\已还1\平台垫付2\呆账3
	public final static String ARREARS_STATUS_CD_WH = "0";//未还
	public final static String ARREARS_STATUS_CD_YH = "1";//已还
	public final static String ARREARS_STATUS_CD_PTDF = "2";//平台垫付
	public final static String ARREARS_STATUS_CD_DZ = "3";//呆账
	
	
	//还款结清方式:REPAY_WAY_CD:初始状态0\正常还款1\提前还款2\平台垫付3
	public final static String REPAY_WAY_CD_CS = "0";//初始化0
	public final static String REPAY_WAY_CD_ZC = "1";//正常还款1
	public final static String REPAY_WAY_CD_TQ = "2";//提前还款2
	public final static String REPAY_WAY_CD_DF = "3";//平台垫付3
	
	//平台是否垫款:PLAT_REPAYED_FLAG_CD:是1\否0
	public final static String PLAT_REPAYED_FLAG_CD_NO = "0";//否
	public final static String PLAT_REPAYED_FLAG_CD_YES = "1";//是
	
	//借款状态 LOAN_STATUS_CD:未结清0\结清1\展期2\逾期3\平台垫款4\呆账5
	public final static String LOAN_STATUS_CD_WJQ = "0";
	public final static String LOAN_STATUS_CD_JQ = "1";
	public final static String LOAN_STATUS_CD_ZQ = "2";
	public final static String LOAN_STATUS_CD_YQ = "3";
	public final static String LOAN_STATUS_CD_PTDF = "4";
	public final static String LOAN_STATUS_CD_DZ = "5";
	
	//借款申请状态 APP_STATUS_CD:借款申请中0\申请通过1\放款2\还款中3\已结清4
	public final static String APP_STATUS_CD_SQ = "0";
	public final static String APP_STATUS_CD_SQTG = "1";
	public final static String APP_STATUS_CD_FK = "2";
	public final static String APP_STATUS_CD_HK = "3";
	public final static String APP_STATUS_CD_JQ = "4";
	
	//是否已经分配收益 IS_INCOME_CD:是1\否0
	public final static String IS_INCOME_CD_CD_YES = "1"; //是
	public final static String IS_INCOME_CD_NO = "0";//否
	
	// 是否标识
	public final static String YES_FLAG = "1";
	public final static String NO_FLAG = "0";
	
	// 收益类型INCOME_TYPE_CD:债权0\理财1
	public static final String INCOME_TYPE_CD_CREDITOR = "0";
	public static final String INCOME_TYPE_CD_FINANCIAL= "1";
	
	//收益来源类型 INCOME_SOUR_TYPE_CD:借款人还款0
	public static final String INCOME_SOUR_TYPE_CD_REPAY = "0";
	

	/** 工作流岗位 */
	// 风控岗
	public static final String WORKITEM_ROLE_RISK = "400001";
	public static final String WORKITEM_ROLE_RISK_TASK = "风控岗审核";
	// 财务专员
	public static final String WORKITEM_ROLE_COMP = "500002";
//	public static final String WORKITEM_ROLE_COMP_TASK = "财务专员";
	public static final String WORKITEM_ROLE_COMP_TASK = "合规岗审核";
	// 客户经理
	public static final String WORKITEM_ROLE_ACCOUNT = "300001";
	public static final String WORKITEM_ROLE_ACCOUNT_TASK = "客户经理岗";
	// 财务经理
	public static final String WORKITEM_ROLE_FINANICAL = "500001";
//	public static final String WORKITEM_ROLE_FINANICAL_TASK = "财务经理";
	public static final String WORKITEM_ROLE_FINANICAL_TASK = "财务管理审核";
	
	// 客服人员
	public static final String WORKITEM_ROLE_CUST = "600002";
	public static final String WORKITEM_ROLE_CUST_TASK = "客服人员";
		
	/** 工作流环节 */
	// 0级审批(流程发起环节)
	public static final String WORKITEM_NODE_INIT = "0";
	// 1级审批
	public static final String WORKITEM_NODE_ONE = "1";
	// 2级审批
	public static final String WORKITEM_NODE_TWO = "2";
	// 3级审批
	public static final String WORKITEM_NODE_THR = "3";
	
	/** 工作流状态 */
	// 工作流状态--无流程状态
	public static final String WORKITEMID_NORMAL = "0";
	// 工作流状态--关联模块导致进入工作流
	public static final String WORKITEMID_RELATION = "-1";
	
	/** 审批状态 */
	// 审批中
	public static final String APPROVAL_STATUS_PENDING ="0";
	// 审批通过
	public static final String APPROVAL_STATUS_SUCCESS ="1";
	// 审批失败
	public static final String APPROVAL_STATUS_FAILURE ="2";
	
	/** 明细类型 */
	// 明细类型-新增
	public static final String DETAIL_TYPE_INSERT = "0";
	// 明细类型-修改
	public static final String DETAIL_TYPE_UPDATE = "1";
	// 明细类型-pm币
	public static final String DETAIL_TYPE_PM = "2";
	// 明细类型-账号余额
	public static final String DETAIL_TYPE_ACCOUNTAMT = "3";
	// 明细类型-冻结金额
	public static final String DETAIL_TYPE_FREEZE = "4";
	
	/**交易类型:
	 * 充值、提现、提现管理费、充值管理费、借款、还款、投资、退回投资、第三方、风险准备金、债权转让管理费、接手奖金、债权转让、资金回收、利息收益、平台收益抽成
	 * 展期借款管理费、逾期借款管理费、借款管理费 、借款申请、银行卡充值*/
	// 交易类型-- 充值
	public static final String TRADE_TYPE_RECHARGE = "0";
	// 交易类型-- 提现
	public static final String TRADE_TYPE_WITHDRAW = "1";
	// 交易类型-- 投资
	public static final String TRADE_TYPE_CREDITOR = "2";
	//交易类型-- 借款
	public static final String TRADE_TYPE_JK = "3";
	//交易类型-- 正常还款
	public static final String TRADE_TYPE_HK = "4";
	//交易类型-- 提前还款
	public static final String TRADE_TYPE_TQHK = "5";
	//交易类型-- 展期还款
	public static final String TRADE_TYPE_ZQHK = "6";
	//交易类型-- 逾期还款
	public static final String TRADE_TYPE_YQHK = "7";
	//交易类型-- 平台垫付
	public static final String TRADE_TYPE_PTDF = "8";
	//交易类型-- 提现管理费
	public static final String TRADE_TYPE_TXGLF = "9";
	//交易类型-- 充值管理费
	public static final String TRADE_TYPE_CZGLF = "10";
	//交易类型-- 退回投资
	public static final String TRADE_TYPE_THTZ = "11";
	//交易类型-- 归还风险准备金
	public static final String TRADE_TYPE_GHFXZBJ = "12";
	//交易类型-- 提取风险准备金
	public static final String TRADE_TYPE_TQFXZBJ = "13";
	//交易类型-- 债权转让管理费
	public static final String TRADE_TYPE_ZQZRGLF = "14";
	//交易类型-- 接手奖金
	public static final String TRADE_TYPE_JSJJ = "15";
	//交易类型-- 债权转让
	public static final String TRADE_TYPE_ZQZR = "16";
	//交易类型-- 资金回收
	public static final String TRADE_TYPE_ZJHS = "17";
	//交易类型-- 利息收益
	public static final String TRADE_TYPE_LXSY = "18";
	//交易类型-- 平台收益抽成
	public static final String TRADE_TYPE_PTSYCC = "19";
	//交易类型-- 展期管理费
	public static final String TRADE_TYPE_ZQGLF = "20";
	//交易类型-- 逾期管理费
	public static final String TRADE_TYPE_YQGLF = "21";
	//交易类型-- 借款管理费
	public static final String TRADE_TYPE_JKGLF = "22";
	//交易类型-- 提前还款违约金
	public static final String TRADE_TYPE_TQHKWYJ = "23";
	//交易类型-- 借款申请
	public static final String TRADE_TYPE_JKSQ = "24";
	//交易类型-- 投资满标
	public static final String TRADE_TYPE_TZMB = "25";
	// 交易类型-- 银行卡充值
	public static final String TRADE_TYPE_RECHARGE_BANK = "26";
	// 交易类型--推广奖金
	public static final String TRADE_TYPE_PROMOTION = "27";
	// 交易类型--第三方代还
	public static final String TRADE_TYPE_ZHBX = "28";
	//推荐人提成
	public static final String TRADE_TYPE_TJTC = "29";
	//推荐奖励
	public static final String TRADE_TYPE_TJJL = "30";
	//冻结金额 
	public static final String TRADE_TYPE_FREEZE = "31";
	
	public static final String TRADE_TYPE_UNFREEZE = "32";
	// 退回提现
	public static final String TRADE_TYPE_CANCEL_WITHDRAW = "33";
	// 退回管理费
	public static final String TRADE_TYPE_CANCEL_TXGLF = "34";
	
	public static final String TRADE_TYPE_VIP_INCOME = "35";
	
	/*业务参数:提现管理费、充值管理费、债权转让管理费、展期借款管理费、逾期借款管理费、借款管理费、风险准备金比例、平台收益费率
	 *宽限期、逾期上浮比例、展期上浮比例、未满标后$DAY天,投资金额自动归还到投资者账户*/
	//业务参数-- 提现管理费率
	public static final String PARAMETER_CODE_TXGLFL = "TXSXF";
	//业务参数-- 充值管理费率
	public static final String PARAMETER_CODE_CZGLFL = "CZSXF";
	//业务参数-- 债权转让管理费率
	public static final String PARAMETER_CODE_ZQZRGLFL = "ZQZRGLFL";
	//业务参数-- 展期借款管理费率
	public static final String PARAMETER_CODE_ZQGLFL = "ZQGLFL";
	//业务参数-- 逾期借款管理费率
	public static final String PARAMETER_CODE_YQGLFL = "YQGLFL";
	//业务参数-- 借款管理费率
	public static final String PARAMETER_CODE_JKGLFL = "JKGLFL";
	//业务参数-- 一年以上标借款管理费率
	public static final String PARAMETER_CODE_YNBFL = "YNBFL";
	//业务参数-- 风险准备金比例
	public static final String PARAMETER_CODE_FXZBJBL= "FXZBJBL";
	//业务参数-- 平台收益费率
	public static final String PARAMETER_CODE_PTSYFL = "PTSYFL";
	//业务参数-- 宽限期天数
	public static final String PARAMETER_CODE_KXQTS = "KXQTS";
	//业务参数-- 逾期上浮比例
	public static final String PARAMETER_CODE_YQSFBL = "YQSFBL";
	//业务参数-- 展期上浮比例
	public static final String PARAMETER_CODE_ZQSFBL = "ZQSFBL";
	//业务参数-- 提前还款违约金比例
	public static final String PARAMETER_CODE_TQHKWYJBL = "TQHKWYJBL";
	//业务参数-- 未满标后$DAY天,投资金额自动归还到投资者账户
	public static final String PARAMETER_CODE_WMBXTLB = "WMBXTLB";
	//业务参数-- X天前自动发短信提醒还款
	public static final String PARAMETER_CODE_HKTX = "HKTX";
	
	public static final String PARAMETER_VIP_INCOME_SCALE = "VIP_INCOME_SCALE";
	
	
	/*费用类型:COST_TYPE_CD:提现费用0\充值费用1\借款服务费2\展期管理费3\逾期管理费4\债权转让管理费5\提前还款违约金6*/
	public static final String COST_TYPE_CD_TXFY = "0";
	public static final String COST_TYPE_CD_CZFY = "1";
	public static final String COST_TYPE_CD_JKFY = "2";
	public static final String COST_TYPE_CD_ZQFY = "3";
	public static final String COST_TYPE_CD_YQFY = "4";
	public static final String COST_TYPE_CD_ZQZRFY = "5";
	public static final String COST_TYPE_CD_TQHKWYJ = "6";
	
	/*扣费状态:COST_STATUS_CD:未扣费0\已扣费1\失效2*/
	public static final String COST_STATUS_CD_WKF = "0";
	public static final String COST_STATUS_CD_YKF = "1";
	public static final String COST_STATUS_CD_SX = "2";
	
	/*关联类型*/
	public static final String RELATE_TYPE_CD_LOAN = "0"; //借款
	public static final String RELATE_TYPE_CD_CREDITOR = "1";//债权
	public static final String RELATE_TYPE_CD_REPAY = "2";//还款
	public static final String RELATE_TYPE_CD_REPAYPLAN = "3";//还款计划
	public static final String RELATE_TYPE_CD_ARREAR= "4";//欠款
	public static final String RELATE_TYPE_CD_CREDITORTRAN = "5";//债权转让
	
	/**
	 * 短信发送交易型态
	 */ 
	// 借款
	public static final String EVENTTYPE_LOAN = "0";
	// 放款
	public static final String EVENTTYPE_CREDITOR ="1";
	// 还款
	public static final String EVENTTYPE_REAPY = "2";
	// 冻结金额
	public static final String EVENTTYPE_FREEZE = "3";
	//注册
	public static final String EVENTTYPE_REGISTER = "4";
	//找回密码
	public static final String EVENTTYPE_FINDPWD = "5";
	//充值
	public static final String EVENTTYPE_RECHARGE = "6";
	//提现
	public static final String EVENTTYPE_DRAWAL = "7";
	//投标
	public static final String EVENTTYPE_TRENDER = "8";
	//回款
	public static final String EVENTTYPE_BACK = "9";
	//设置交易密码
	public static final String EVENTTYPE_TRADEPWD = "10";
	//推荐奖励
	public static final String EVENTTYPE_RECOMMEND = "11";
	//推荐提成
	public static final String EVENTTYPE_DEDUCT = "12";
	//收益
	public static final String EVENTTYPE_INCOME = "13";
	//开始计算收益提醍
	public static final String EVENTTYPE_INCOME_CAL = "14";
	//还款到期提醒
	public static final String EVENTTYPE_REAPY_REMIND = "15";
	
	public static final String EVENTTYPE_VIP_INCOME = "16";
	//退回投资
	public static final String EVENTTYPE_RETURN_TRENDER = "17";
	//
	public static final String EVENTTYPE_CANCEL_LOAN = "18";
	
	/** 额度服务常量 */
	// 额度关联类型--客户id
	public static final String CREDITLIMIT_RELTYPE_CUST = "0";
	//额度占用
	public static final String CREDITLIMIT_AMT_USE = "0";
	//额度恢复
	public static final String CREDITLIMIT_AMT_RECOVERY = "1";
	// 额度状态--未生效
	public static final String CREDITLIMIT_STATUE_UNUSABLE = "0";
	// 额度状态--生效
	public static final String CREDITLIMIT_STATUE_USABLE = "1";
	// 额度状态--冻结
	public static final String CREDITLIMIT_STATUE_FZ = "2";
	//默认额度
	public static final BigDecimal DEFALT_CREDITLIMIT = new BigDecimal(99999999999999.00);
	
	//默认期限
	public static final int DEFALT_TIME_LIMIT=360;
	//默认评分
	public static final int CUST_SCPRE=0;
	/** 支付模块 */
	// 充值方式--网上银行
	public static final String PAYOUTTYPE_ONLINE = "1";
	// 充值方式--理财卡
	public static final String PAYOUTTYPE_CARD = "2";
	// 充值卡状态--生效
	public static final String RECHARGE_STATUS_INIT = "0";
	// 充值卡状态--充值中
	public static final String RECHARGE_STATUS_WAIT = "1";
	// 充值卡状态--已充值
	public static final String RECHARGE_STATUS_USE = "2";
	// 充值卡状态--作废
	public static final String RECHARGE_STATUS_DISCARD = "3";
	// 充值卡状态--已导出
	public static final String RECHARGE_STATUS_EXPORT = "4";
	
	/** 提现申请状态 */
	// 审批中
	public static final String WITHDRAWALS_STATUS_PENDING ="0";
	// 审批通过
	public static final String WITHDRAWALS_STATUS_SUCCESS ="1";
	// 审批失败
	public static final String WITHDRAWALS_STATUS_FAILURE ="2";
	// 已导出
	public static final String WITHDRAWALS_STATUS_EXPORT = "3";
	
	/** 账号模块*/
	// 金额操作类型 -- 账号余额
	public static final String ACCOUNT_AMT = "0";
	// 金额操作类型 -- 冻结金额
	public static final String ACCOUNT_AMT_FREEZE = "1";
	
	public static final String ACCOUNT_AMT_UNFREEZE = "2";
	
	//期限单位：天
	public static final String TERM_UNIT_CD_DATE = "0";
	//期限单位：月
	public static final String TERM_UNIT_CD_MONTH = "1";
	//期限单位：年
	public static final String TERM_UNIT_CD_YEAR = "2";
	//文集上传路径
	public static final String PARM_FILE_UPLOAD_DIR = "fileUpload/";
	
	public static final String PARM_FILE_UPLOAD_REALPATH = "fileUploadDir";
	//认证关联类型:会员
	public static final String AUTH_REL_TYPE_CD_CUST = "0";
	//认证关联类型:借款申请
	public static final String AUTH_REL_TYPE_CD_LOAN = "1";
	
	public static final String LOGIN_INFO_SESSION = "loginInfo";
	//最小份数金额参数
	public static final String PARM_MIN_TENDER_AMT = "MIN_TENDER_AMT";
	
	/**贷后产生方式:1:手动，2：自动 */
	public static final String POSTLOAN_TYPE_CD_SD = "1";
	public static final String POSTLOAN_TYPE_CD_AUTO = "2";
	
	/**贷后状态:1:新增，2：逾期 ，3：贷后完成申请中，4：完成*/
	public static final String POSTLOAN_STATUS_CD_ADD = "1";
	public static final String POSTLOAN_STATUS_CD_OVER = "2";
	public static final String POSTLOAN_STATUS_CD_APPLY = "3";
	public static final String POSTLOAN_STATUS_CD_END = "4";
	
	/**证件类型:0:身份证，1:户口簿,2：护照，3：军官证，4：士兵证*/
	public static final String CERTIFICATE_TYPE_CD_SF = "0";
	public static final String CERTIFICATE_TYPE_CD_HK = "1";
	public static final String CERTIFICATE_TYPE_CD_HZ = "2";
	public static final String CERTIFICATE_TYPE_CD_JG = "3";
	public static final String CERTIFICATE_TYPE_CD_SB = "4";
	
	/**信用等级:AA(分数100以上），A（90-100），B(80-90)，C(70-80)，D(50-70),E(50以下)*/
	public static final String CREDITRATE_AA = "AA";
	public static final String CREDITRATE_A = "A";
	public static final String CREDITRATE_B = "B";
	public static final String CREDITRATE_C = "C";
	public static final String CREDITRATE_D = "D";
	public static final String CREDITRATE_E = "E";
	
	/** 充值编号 */
	public static final String SEQ_PAY_NUM = "PAYNUM";
	/** 支付信息情况 0（初始化），1（成功），2（失败） */
	public static final String PAY_STATUS_INIT = "0";
	public static final String PAY_STATUS_SUCCESS = "1";
	public static final String PAY_STATUS_FAILURE = "2";
	public static final String PAY_STATUS_SUCCESS_CODE = "88";//支付平台返回成功代码
	
	/**债权前7天,不能转让 */
	public static final int NoTranDay =  7;
	
	/** 提醒模板代码 */
	public static final String SMS_OVERDUE_NOTICE = "overdue";
	public static final String EMAIL_OVERDUE_NOTICE = "emailOverdue";
	
	public static final String SMS_STATUS_SUCCESS = "100";
	
	//绑定邮箱
	public static final String BINGING_EMAIL = "1";
	//解绑邮箱
	public static final String UNBINDNG_EMAIL = "2";
	
	//邮件验证有效时间
	public static final long EMAIL_BING_HOUR = 1;
	//邮件验证有效时间
	public static final long EMAIL_AUTH_HOUR = 24;
	
	//借款意向状态【未处理】
	public static final String LOAN_INT_STATUS = "0";
	
	//最小审批提现金额
	public static final String MIN_APPROVAL_CASH = "MIN_APPROVAL_CASH";
	
	//获取Pm币类型
	public static final String PM_TYPE_CARD_AUT = "1";//推广获得PM币
	public static final String PM_TYPE_INVESTMENT = "2";//投资获得PM币
	
	public static final String PROMOT_LEVEL0 = "LEVEL0";//初始等级
	public static final String PROMOT_PUTOUT_STATUS =  "0000000000";//奖金发放状态
	
	public static final String IS_OPEN = "YES";//现金奖励或者PM币奖励开启标识
	
	//获取推广类型
	public static final String PROMOT_TYPE_REG = "1";//用户注册推广
	
	//验证发送类型
	public static final String PHONE_SEND_TYPE_REGI = "1";//手机注册
	public static final String PHONE_SEND_TYPE_EDITPWD = "2";//密码修改
	
	public static final String RESP_NOT_FOUND = "404"; // 找不到服务
	public static final String RESP_FORBIDDEN = "403"; // 禁止访问
	
	/** 业务响应代码定义* */
	public static final String USER_NOT_FOUND = "901"; // 用户不存在
	public static final String USER_ERROR = "902"; // 用户名或密码错误
	public static final String USER_LOCKED = "903"; // 用户被锁定
	
	public static final String USERINFO_SYN_ERROR = "901"; // 操作类型为空
	public static final String USERINFO_SYN_FAIL = "500"; // 同步失败
	public static final String USERINFO_SYN_SUCESS = "200"; // 同步成功
	//数据同步操作类型
	public static final String USERINFO_SYN_REGISTER = "1"; // 注册
	public static final String USERINFO_SYN_MOD = "2"; // 修改用户信息
	public static final String USERINFO_SYN_AUTH = "3"; // 实名认证
	public static final String USERINFO_SYN_CHG = "4"; // 修改密码
	//标的类型 （按还款期限）
	public static final String PROJECT_ONE_MON = "PROJECT_ONE_MON"; // 1月标
	public static final String PROJECT_THREE_MON = "PROJECT_THREE_MON"; // 3月标
	public static final String PROJECT_SIX_MON = "PROJECT_SIX_MON"; // 6月标
	public static final String PROJECT_TWELVE_MON = "PROJECT_TWELVE_MON"; // 12月标
	//推荐奖励金额
	public static final String REFEREE_PER = "REFEREE_PER"; // 推荐奖励
	public static final String REFEREE_TEN = "REFEREE_TEN"; // 推荐10人奖励
	public static final String REFEREE_FIFTY = "REFEREE_FIFTY"; // 推荐50人奖励
	public static final String REFEREE_HUNDRED = "REFEREE_HUNDRED"; // 推荐100人奖励
	public static final String REFEREE_TWO_HUNDRED = "REFEREE_TWO_HUNDRED"; // 推荐200人奖励
	public static final String REFEREE_THR_HUNDRED = "REFEREE_THR_HUNDRED"; // 推荐300人奖励
	public static final String REFEREE_FIVE_HUNDRED = "REFEREE_FIVE_HUNDRED"; // 推荐500人奖励
	
	
	public static final BizAutoTenderConfigBO AUTO_TENDER_CONFIG = new BizAutoTenderConfigBO();
	static{
		AUTO_TENDER_CONFIG.setYearRateBe(new BigDecimal("10.00"));
		AUTO_TENDER_CONFIG.setYearRateEn(new BigDecimal("20.00"));
		AUTO_TENDER_CONFIG.setLoanPeriodBe(1);
		AUTO_TENDER_CONFIG.setLoanPeriodEn(12);
		AUTO_TENDER_CONFIG.setAccBal(new BigDecimal("1000.00"));
		AUTO_TENDER_CONFIG.setCreditRateBe("AA");
		AUTO_TENDER_CONFIG.setCreditRateEn("E");
		AUTO_TENDER_CONFIG.setAccRetain(new BigDecimal("1000.00"));
		AUTO_TENDER_CONFIG.setEachMaxBid(new BigDecimal("1000.00"));
	}
	/** 支付new **/
	/**
	 * 网关版本号  2.1
	 */
	public static final String PAYMENT_VERSION = "2.1";//
	/**
	 * 网关语言版本 1-中文
	 */
	public static final String PAYMENT_LANGUAGE = "1";//
	/**
	 * 交易代码
	 */
	public static final String PAYMENT_TRANCODE = "8888";
	/**
	 * 商户代码
	 */
//	public static final String PAYMENT_MERCHANTID = "0000003358";
	public static final String PAYMENT_MERCHANTID = "0000009134";
	/**
	 * 商户提取佣金金额
	 */
	public static final String PAYMENT_FEEAMT = "0";
	/**
	 * 币种
	 */
	public static final String PAYMENT_CURRENCYTYPE = "156";
	
	/**
	 * 国付宝转入账户
	 */
//	public static final String PAYMENT_VIRCARDNOIN = "0000000001000000584";
	public static final String PAYMENT_VIRCARDNOIN = "0000000002000003181";
	/**
	 * 订单是否允许重复提交
	 */
	public static final String PAYMENT_ISREPEATSUBMIT = "1";
	/**
	 * 用户浏览器IP
	 */
	public static final String PAYMENT_TRANIP = "127.0.0.1";
	/**
	 * 商户识别码
	 */
//	public static final String PAYMENT_VERFICATIONCODE = "12345678";
	public static final String PAYMENT_VERFICATIONCODE = "915870001aaaaa";
	
	 /**
     * 国付宝服务器时间，反钓鱼时使用
     */
//	public static String GOPAY_SERVER_TIME_URL = "https://mertest.gopay.com.cn/PGServer/time";
	public static String GOPAY_SERVER_TIME_URL = "https://gateway.gopay.com.cn/time.do";
	/**
	 *身份认证返回成功
	 */
	public static final String VALIDATOR_SUCCESS = "0";
	/**
	 * 查询的身份证号码与姓名一致
	 */
	public static final String VALIDATOR_UNIFORMITY = "3";
	
	//用户同步操作类型
	/**
	 * 修改用户信息
	 */
	public static final String USERINFOSYNC_OPERATE_MOD = "2";
	/**
	 * 注册
	 */
	public static final String USERINFOSYNC_OPERATE_REG = "1";
	/**
	 * 实名认证
	 */
	public static final String USERINFOSYNC_OPERATE_AUTH = "3";
	/**
	 * 修改密码
	 */
	public static final String USERINFOSYNC_OPERATE_CHG = "4";
	/**
	 * 国付宝充值成功代码
	 */
	public static final String PAY_SUCCESS = "0000";
	/**
     * 国付宝提供给商户调试的网关地址
     */
//    public static String gopay_gateway = "https://mertest.gopay.com.cn/PGServer/Trans/WebClientAction.do";
    public static final String gopay_gateway = "https://gateway.gopay.com.cn/Trans/WebClientAction.do";
	
	/**
     * 字符编码格式，目前支持 GBK 或 UTF-8
     */
	public static final String input_charset = "GBK";
	
	public static final String DEFALT_REFEREECODE = "888888";
	
	//通联支付常量定义
	/**
	 * 版本号
	 */
	public static final String TL_VERSION = "v1.0";
	/**
	 * 语言
	 */
	public static final String TL_LANGUAGE = "1";
	/**
	 * 支付方式
	 */
	public static final String TL_PAYTYPE = "0";
	/**
	 * 签名类型
	 */
	public static final String TL_SIGNTYPE = "1";
	/**
	 * 币别
	 */
	public static final String TL_ORDER_CURRENCY = "0";
	/**
	 * 订单过期时间
	 */
	public static final String TL_ORDER_EXPIREDATETIME = "0";
	/**
	 * 
	 */
	public static final String TL_INPUT_CHARSET = "1";
	/**
	 * 支付服务商-国付宝
	 */
	public static final String PAY_SERVICE_GFB = "0";
	/**
	 * 通联
	 */
	public static final String PAY_SERVICE_TL = "1";
	//TODO 上生产需切换
	 public static String GATEWAY_URL = "http://ceshi.allinpay.com/gateway/index.do";
//	 public static final String GATEWAY_URL = "https://service.allinpay.com/gateway/index.do";
	//TODO 上生产需切换
	 public static String MOBILEPAYMENT_URL = "http://ceshi.allinpay.com/mobilepayment/mobile/SaveMchtOrderServlet.action";
//	 public static final String MOBILEPAYMENT_URL = "https://service.allinpay.com/mobilepayment/mobile/SaveMchtOrderServlet.action";
	//TODO 上生产需切换
	 public static final String WITHDRAWAL_TRAN_URL = "https://113.108.182.3/aipg/ProcessServlet";
	 //生产
//	 public static final String WITHDRAWAL_TRAN_URL = "https://tlt.allinpay.com/aipg/ProcessServlet";
	 
	 public static final String WITHDRAWAL_QUERY_TRX_CODE = "200004";
	 
	 public static final String WITHDRAWAL_TRX_CODE = "100014";
	 
	 public static final String WITHDRAWAL_BUSICODE = "09900";
	 
	 public static final String WD_ACCOUNT_PROP = "0";
	 
	 public static final String WD_ACCOUNT_TYPE = "00";
	 
//	 public static final String WD_CERPATH = "config\\20060400000044502.cer";
	 
//	 public static final String WD_PFXPATH = "D:/config/20060400000044502.p12";
	 
//	 public static final String WD_TLTCERPATH= "D:/config/allinpay-pds.cer";
	//提现状态-成功
	 public static final String WD_STATUS_S = "0";
	//提现状态-处理中
	 public static final String WD_STATUS_P = "1";
	//提现状态-失败
	 public static final String WD_STATUS_F = "2";
	 //提现状态-结束（已处理）
	 public static final String WD_STATUS_D = "3";
	 //通联查询类型--按提交日期
	 public static final int WD_QUERY_TYPE_SUBMIT = 1;
	 //通联查询类型--按完成日期
	 public static final int WD_QUERY_TYPE_DONE = 0;
	//通联查询交易状态--全部
	 public static final int WD_QUERY_STATUS_ALL = 2;
	 //通联查询返回成功状态
	 public static final String WD_RETURN_CODE_SUCCESS = "0000";
	//通联查询返回成功状态
	 public static final String WD_RETURN_SUCCESS = "4000";
	 
	 //通联end
	 //短信服务：云通讯 
	 public static final String MS_SERVICE_YUN = "0";
	//短信服务：天元
	 public static final String MS_SERVICE_SUN = "1";
	 //
	 
	 /**
	  *用户是否冻结-是
	  */
	 public static final String USER_FREEZE_YES = "1";
	 /**
	  * 用户是否冻结-否
	  */
	 public static final String USER_FREEZE_NO = "0";
	 /**
	  * 合同编号命名
	  */
	 public static final String CONTRACTNUM_NS = "CT-";
	 /**
	  * 推广状态-初始（不符合推广条件）
	  */
	 public static final String REFEREE_STATUS_INITIAL = "0";
	 /**
	  * 推广状态-申请中
	  */
	 public static final String REFEREE_STATUS_APPLY = "1";
	 /**
	  * 推广状态-已审核
	  */
	 public static final String REFEREE_STATUS_AGREE = "2";
	 /**
	  * 推广状态-不通过
	  */
	 public static final String REFEREE_STATUS_NOT_AGREE = "7";
	 /**
	  * 再次申请中
	  */
	 public static final String REFEREE_STATUS_APPLY_AGAIN = "4";
	 /**
	  * 推广状态-两次不通过
	  */
	 public static final String REFEREE_STATUS_NOTAGREE_TWICE = "5";
	 /**
	  * 第三次申请中
	  */
	 public static final String REFEREE_STATUS_APPLY_THIRD = "6";
	 /**
	  * 推广状态-一次不通过
	  */
	 public static final String REFEREE_STATUS_NOTAGREE_AGAIN = "3";
	
}
