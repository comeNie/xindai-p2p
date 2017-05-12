package com.qfw.model.vo.transaction;

import java.util.List;

public class CisReportsVO {
	
	private String batNo;
	private String unitName;
	private String subOrgan;
	private String queryUserID;
	private String queryCount;
	private String receiveTime;
	
	private CisReportVO cisReport = new CisReportVO();
	
	public class CisReportVO{
		private String reportID;
		private String buildEndTime;
		private String queryReasonID;
		private String subReportTypes;
		private String treatResult;
		private String subReportTypesShortCaption;
		private String refID;
		private String hasSystemError;
		private String isFrozen;
		private QueryConditionsVO queryConditions = new QueryConditionsVO();
		public class QueryConditionsVO{
			
			private List<QueryCreditReqRtnVO> creditReqRtnVOs;

			public List<QueryCreditReqRtnVO> getCreditReqRtnVOs() {
				return creditReqRtnVOs;
			}

			public void setCreditReqRtnVOs(List<QueryCreditReqRtnVO> creditReqRtnVOs) {
				this.creditReqRtnVOs = creditReqRtnVOs;
			}



			public class QueryCreditReqRtnVO{
				private String name;
				private String caption;
				private String value;
				public String getName() {
					return name;
				}
				public void setName(String name) {
					this.name = name;
				}
				public String getCaption() {
					return caption;
				}
				public void setCaption(String caption) {
					this.caption = caption;
				}
				public String getValue() {
					return value;
				}
				public void setValue(String value) {
					this.value = value;
				}
				
			}
			
		}
		
		private PoliceCheckInfoVO policeCheckInfo = new PoliceCheckInfoVO();
		public class PoliceCheckInfoVO{
			private String subReportType;
			private String subReportTypeCost;
			private String treatResult;
			private String errorMessage;
			private CreditDetailVO creditDetailVO = new CreditDetailVO();
			public String getSubReportType() {
				return subReportType;
			}
			public void setSubReportType(String subReportType) {
				this.subReportType = subReportType;
			}
			public String getSubReportTypeCost() {
				return subReportTypeCost;
			}
			public void setSubReportTypeCost(String subReportTypeCost) {
				this.subReportTypeCost = subReportTypeCost;
			}
			public String getTreatResult() {
				return treatResult;
			}
			public void setTreatResult(String treatResult) {
				this.treatResult = treatResult;
			}
			public String getErrorMessage() {
				return errorMessage;
			}
			public void setErrorMessage(String errorMessage) {
				this.errorMessage = errorMessage;
			}
			public CreditDetailVO getCreditDetailVO() {
				return creditDetailVO;
			}
			public void setCreditDetailVO(CreditDetailVO creditDetailVO) {
				this.creditDetailVO = creditDetailVO;
			}
			
		}
		public String getReportID() {
			return reportID;
		}
		public void setReportID(String reportID) {
			this.reportID = reportID;
		}
		public String getBuildEndTime() {
			return buildEndTime;
		}
		public void setBuildEndTime(String buildEndTime) {
			this.buildEndTime = buildEndTime;
		}
		public String getQueryReasonID() {
			return queryReasonID;
		}
		public void setQueryReasonID(String queryReasonID) {
			this.queryReasonID = queryReasonID;
		}
		public String getSubReportTypes() {
			return subReportTypes;
		}
		public void setSubReportTypes(String subReportTypes) {
			this.subReportTypes = subReportTypes;
		}
		public String getTreatResult() {
			return treatResult;
		}
		public void setTreatResult(String treatResult) {
			this.treatResult = treatResult;
		}
		public String getSubReportTypesShortCaption() {
			return subReportTypesShortCaption;
		}
		public void setSubReportTypesShortCaption(String subReportTypesShortCaption) {
			this.subReportTypesShortCaption = subReportTypesShortCaption;
		}
		public String getRefID() {
			return refID;
		}
		public void setRefID(String refID) {
			this.refID = refID;
		}
		public String getHasSystemError() {
			return hasSystemError;
		}
		public void setHasSystemError(String hasSystemError) {
			this.hasSystemError = hasSystemError;
		}
		public String getIsFrozen() {
			return isFrozen;
		}
		public void setIsFrozen(String isFrozen) {
			this.isFrozen = isFrozen;
		}
		public QueryConditionsVO getQueryConditions() {
			return queryConditions;
		}
		public void setQueryConditions(QueryConditionsVO queryConditions) {
			this.queryConditions = queryConditions;
		}
		public PoliceCheckInfoVO getPoliceCheckInfo() {
			return policeCheckInfo;
		}
		public void setPoliceCheckInfo(PoliceCheckInfoVO policeCheckInfo) {
			this.policeCheckInfo = policeCheckInfo;
		}
		
	}

	public String getBatNo() {
		return batNo;
	}

	public void setBatNo(String batNo) {
		this.batNo = batNo;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getSubOrgan() {
		return subOrgan;
	}

	public void setSubOrgan(String subOrgan) {
		this.subOrgan = subOrgan;
	}

	public String getQueryUserID() {
		return queryUserID;
	}

	public void setQueryUserID(String queryUserID) {
		this.queryUserID = queryUserID;
	}

	public String getQueryCount() {
		return queryCount;
	}

	public void setQueryCount(String queryCount) {
		this.queryCount = queryCount;
	}

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public CisReportVO getCisReport() {
		return cisReport;
	}

	public void setCisReport(CisReportVO cisReport) {
		this.cisReport = cisReport;
	}
	
	public CreditDetailVO getCreditDetailVO() {
		return getCisReport().getPoliceCheckInfo().getCreditDetailVO();
	}

}