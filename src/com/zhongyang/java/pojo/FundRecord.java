package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.zhongyang.java.vo.fund.FundRecordOperation;
import com.zhongyang.java.vo.fund.FundRecordStatus;
import com.zhongyang.java.vo.fund.FundRecordType;

public class FundRecord {
    private String id;

    private String dtype;

    private BigDecimal amount;//金额
    
    private BigDecimal aviAmount;//交易后余额

    private String description;//可能为失败的提示信息

    private FundRecordOperation operation;//操作

    private String orderid;//交易订单号

    private String recordpriv;//扩展信息

    private FundRecordStatus status;//资金状态

    private Date timerecorded;//记录时间

    private String transactionid;//交易流水号

    private FundRecordType type;//资金记录类型

    private String entityid;//自己记录对应的实体 未用到

    private String fundAccountId;//用户自己账号ID 未用到

    private String realm;

    private String userId;//用户ID
    
    private  UserFund userFund;		

	public BigDecimal getAviAmount() {
		return aviAmount;
	}

	public void setAviAmount(BigDecimal aviAmount) {
		this.aviAmount = aviAmount;
	}

	public UserFund getUserFund() {
		return userFund;
	}

	public void setUserFund(UserFund userFund) {
		this.userFund = userFund;
	}
	UUID uuid  =  UUID.randomUUID(); 
	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = uuid.toString().toUpperCase();
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype == null ? null : dtype.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }



    public FundRecordOperation getOperation() {
		return operation;
	}

	public void setOperation(FundRecordOperation operation) {
		this.operation = operation;
	}

	public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public String getRecordpriv() {
        return recordpriv;
    }

    public void setRecordpriv(String recordpriv) {
        this.recordpriv = recordpriv == null ? null : recordpriv.trim();
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
    
    

    public Date getTimerecorded() {
        return timerecorded;
    }

    public FundRecordStatus getStatus() {
		return status;
	}

	public void setStatus(FundRecordStatus status) {
		this.status = status;
	}

	public void setTimerecorded(Date timerecorded) {
        this.timerecorded = timerecorded;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid == null ? null : transactionid.trim();
    }

  
    public FundRecordType getType() {
		return type;
	}

	public void setType(FundRecordType type) {
		this.type = type;
	}

	public String getEntityid() {
        return entityid;
    }

    public void setEntityid(String entityid) {
        this.entityid = entityid == null ? null : entityid.trim();
    }

    public String getFundAccountId() {
        return fundAccountId;
    }

    public void setFundAccountId(String fundAccountId) {
        this.fundAccountId = fundAccountId == null ? null : fundAccountId.trim();
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm == null ? null : realm.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public FundRecord(UserFund userFund2, FundAccount fundAccount, FundRecordStatus status2,
			FundRecordOperation operation2, BigDecimal amount2, String orderId2, String transactionId2,
			String description2) {
		// TODO Auto-generated constructor stub
	}


	public FundRecord(String id, String dtype, BigDecimal amount, String description, FundRecordOperation operation, String orderid,
			String recordpriv, FundRecordStatus status, Date timerecorded, String transactionid, FundRecordType type, String userId) {
		super();
		this.id = id;
		this.dtype = dtype;
		this.amount = amount;
		this.description = description;
		this.operation = operation;
		this.orderid = orderid;
		this.recordpriv = recordpriv;
		this.status = status;
		this.timerecorded = timerecorded;
		this.transactionid = transactionid;
		this.type = type;
		this.userId = userId;
	}

	public FundRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "FundRecord [id=" + id + ", dtype=" + dtype + ", amount=" + amount + ", description=" + description
				+ ", operation=" + operation + ", orderid=" + orderid + ", recordpriv=" + recordpriv + ", status="
				+ status + ", timerecorded=" + timerecorded + ", transactionid=" + transactionid + ", type=" + type
				+ ", entityid=" + entityid + ", fundAccountId=" + fundAccountId + ", realm=" + realm + ", userId="
				+ userId + ", userFund=" + userFund + ", uuid=" + uuid + "]";
	}
	
    
}