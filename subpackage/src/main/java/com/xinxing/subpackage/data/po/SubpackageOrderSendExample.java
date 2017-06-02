package com.xinxing.subpackage.data.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubpackageOrderSendExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SubpackageOrderSendExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andOrderidIsNull() {
            addCriterion("orderId is null");
            return (Criteria) this;
        }

        public Criteria andOrderidIsNotNull() {
            addCriterion("orderId is not null");
            return (Criteria) this;
        }

        public Criteria andOrderidEqualTo(String value) {
            addCriterion("orderId =", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidNotEqualTo(String value) {
            addCriterion("orderId <>", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidGreaterThan(String value) {
            addCriterion("orderId >", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidGreaterThanOrEqualTo(String value) {
            addCriterion("orderId >=", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidLessThan(String value) {
            addCriterion("orderId <", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidLessThanOrEqualTo(String value) {
            addCriterion("orderId <=", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidLike(String value) {
            addCriterion("orderId like", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidNotLike(String value) {
            addCriterion("orderId not like", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidIn(List<String> values) {
            addCriterion("orderId in", values, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidNotIn(List<String> values) {
            addCriterion("orderId not in", values, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidBetween(String value1, String value2) {
            addCriterion("orderId between", value1, value2, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidNotBetween(String value1, String value2) {
            addCriterion("orderId not between", value1, value2, "orderid");
            return (Criteria) this;
        }

        public Criteria andSendorderidIsNull() {
            addCriterion("sendOrderId is null");
            return (Criteria) this;
        }

        public Criteria andSendorderidIsNotNull() {
            addCriterion("sendOrderId is not null");
            return (Criteria) this;
        }

        public Criteria andSendorderidEqualTo(String value) {
            addCriterion("sendOrderId =", value, "sendorderid");
            return (Criteria) this;
        }

        public Criteria andSendorderidNotEqualTo(String value) {
            addCriterion("sendOrderId <>", value, "sendorderid");
            return (Criteria) this;
        }

        public Criteria andSendorderidGreaterThan(String value) {
            addCriterion("sendOrderId >", value, "sendorderid");
            return (Criteria) this;
        }

        public Criteria andSendorderidGreaterThanOrEqualTo(String value) {
            addCriterion("sendOrderId >=", value, "sendorderid");
            return (Criteria) this;
        }

        public Criteria andSendorderidLessThan(String value) {
            addCriterion("sendOrderId <", value, "sendorderid");
            return (Criteria) this;
        }

        public Criteria andSendorderidLessThanOrEqualTo(String value) {
            addCriterion("sendOrderId <=", value, "sendorderid");
            return (Criteria) this;
        }

        public Criteria andSendorderidLike(String value) {
            addCriterion("sendOrderId like", value, "sendorderid");
            return (Criteria) this;
        }

        public Criteria andSendorderidNotLike(String value) {
            addCriterion("sendOrderId not like", value, "sendorderid");
            return (Criteria) this;
        }

        public Criteria andSendorderidIn(List<String> values) {
            addCriterion("sendOrderId in", values, "sendorderid");
            return (Criteria) this;
        }

        public Criteria andSendorderidNotIn(List<String> values) {
            addCriterion("sendOrderId not in", values, "sendorderid");
            return (Criteria) this;
        }

        public Criteria andSendorderidBetween(String value1, String value2) {
            addCriterion("sendOrderId between", value1, value2, "sendorderid");
            return (Criteria) this;
        }

        public Criteria andSendorderidNotBetween(String value1, String value2) {
            addCriterion("sendOrderId not between", value1, value2, "sendorderid");
            return (Criteria) this;
        }

        public Criteria andFlowpacksizeIsNull() {
            addCriterion("flowPackSize is null");
            return (Criteria) this;
        }

        public Criteria andFlowpacksizeIsNotNull() {
            addCriterion("flowPackSize is not null");
            return (Criteria) this;
        }

        public Criteria andFlowpacksizeEqualTo(Integer value) {
            addCriterion("flowPackSize =", value, "flowpacksize");
            return (Criteria) this;
        }

        public Criteria andFlowpacksizeNotEqualTo(Integer value) {
            addCriterion("flowPackSize <>", value, "flowpacksize");
            return (Criteria) this;
        }

        public Criteria andFlowpacksizeGreaterThan(Integer value) {
            addCriterion("flowPackSize >", value, "flowpacksize");
            return (Criteria) this;
        }

        public Criteria andFlowpacksizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("flowPackSize >=", value, "flowpacksize");
            return (Criteria) this;
        }

        public Criteria andFlowpacksizeLessThan(Integer value) {
            addCriterion("flowPackSize <", value, "flowpacksize");
            return (Criteria) this;
        }

        public Criteria andFlowpacksizeLessThanOrEqualTo(Integer value) {
            addCriterion("flowPackSize <=", value, "flowpacksize");
            return (Criteria) this;
        }

        public Criteria andFlowpacksizeIn(List<Integer> values) {
            addCriterion("flowPackSize in", values, "flowpacksize");
            return (Criteria) this;
        }

        public Criteria andFlowpacksizeNotIn(List<Integer> values) {
            addCriterion("flowPackSize not in", values, "flowpacksize");
            return (Criteria) this;
        }

        public Criteria andFlowpacksizeBetween(Integer value1, Integer value2) {
            addCriterion("flowPackSize between", value1, value2, "flowpacksize");
            return (Criteria) this;
        }

        public Criteria andFlowpacksizeNotBetween(Integer value1, Integer value2) {
            addCriterion("flowPackSize not between", value1, value2, "flowpacksize");
            return (Criteria) this;
        }

        public Criteria andWhichpackIsNull() {
            addCriterion("whichPack is null");
            return (Criteria) this;
        }

        public Criteria andWhichpackIsNotNull() {
            addCriterion("whichPack is not null");
            return (Criteria) this;
        }

        public Criteria andWhichpackEqualTo(Integer value) {
            addCriterion("whichPack =", value, "whichpack");
            return (Criteria) this;
        }

        public Criteria andWhichpackNotEqualTo(Integer value) {
            addCriterion("whichPack <>", value, "whichpack");
            return (Criteria) this;
        }

        public Criteria andWhichpackGreaterThan(Integer value) {
            addCriterion("whichPack >", value, "whichpack");
            return (Criteria) this;
        }

        public Criteria andWhichpackGreaterThanOrEqualTo(Integer value) {
            addCriterion("whichPack >=", value, "whichpack");
            return (Criteria) this;
        }

        public Criteria andWhichpackLessThan(Integer value) {
            addCriterion("whichPack <", value, "whichpack");
            return (Criteria) this;
        }

        public Criteria andWhichpackLessThanOrEqualTo(Integer value) {
            addCriterion("whichPack <=", value, "whichpack");
            return (Criteria) this;
        }

        public Criteria andWhichpackIn(List<Integer> values) {
            addCriterion("whichPack in", values, "whichpack");
            return (Criteria) this;
        }

        public Criteria andWhichpackNotIn(List<Integer> values) {
            addCriterion("whichPack not in", values, "whichpack");
            return (Criteria) this;
        }

        public Criteria andWhichpackBetween(Integer value1, Integer value2) {
            addCriterion("whichPack between", value1, value2, "whichpack");
            return (Criteria) this;
        }

        public Criteria andWhichpackNotBetween(Integer value1, Integer value2) {
            addCriterion("whichPack not between", value1, value2, "whichpack");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStarttimeIsNull() {
            addCriterion("startTime is null");
            return (Criteria) this;
        }

        public Criteria andStarttimeIsNotNull() {
            addCriterion("startTime is not null");
            return (Criteria) this;
        }

        public Criteria andStarttimeEqualTo(Date value) {
            addCriterion("startTime =", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeNotEqualTo(Date value) {
            addCriterion("startTime <>", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeGreaterThan(Date value) {
            addCriterion("startTime >", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("startTime >=", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeLessThan(Date value) {
            addCriterion("startTime <", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeLessThanOrEqualTo(Date value) {
            addCriterion("startTime <=", value, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeIn(List<Date> values) {
            addCriterion("startTime in", values, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeNotIn(List<Date> values) {
            addCriterion("startTime not in", values, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeBetween(Date value1, Date value2) {
            addCriterion("startTime between", value1, value2, "starttime");
            return (Criteria) this;
        }

        public Criteria andStarttimeNotBetween(Date value1, Date value2) {
            addCriterion("startTime not between", value1, value2, "starttime");
            return (Criteria) this;
        }

        public Criteria andEndtimeIsNull() {
            addCriterion("endTime is null");
            return (Criteria) this;
        }

        public Criteria andEndtimeIsNotNull() {
            addCriterion("endTime is not null");
            return (Criteria) this;
        }

        public Criteria andEndtimeEqualTo(Date value) {
            addCriterion("endTime =", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeNotEqualTo(Date value) {
            addCriterion("endTime <>", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeGreaterThan(Date value) {
            addCriterion("endTime >", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("endTime >=", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeLessThan(Date value) {
            addCriterion("endTime <", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeLessThanOrEqualTo(Date value) {
            addCriterion("endTime <=", value, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeIn(List<Date> values) {
            addCriterion("endTime in", values, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeNotIn(List<Date> values) {
            addCriterion("endTime not in", values, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeBetween(Date value1, Date value2) {
            addCriterion("endTime between", value1, value2, "endtime");
            return (Criteria) this;
        }

        public Criteria andEndtimeNotBetween(Date value1, Date value2) {
            addCriterion("endTime not between", value1, value2, "endtime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}