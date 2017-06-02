package com.xinxing.subpackage.data.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubpackageOrderApiExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SubpackageOrderApiExample() {
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

        public Criteria andProductinfoIsNull() {
            addCriterion("productInfo is null");
            return (Criteria) this;
        }

        public Criteria andProductinfoIsNotNull() {
            addCriterion("productInfo is not null");
            return (Criteria) this;
        }

        public Criteria andProductinfoEqualTo(String value) {
            addCriterion("productInfo =", value, "productinfo");
            return (Criteria) this;
        }

        public Criteria andProductinfoNotEqualTo(String value) {
            addCriterion("productInfo <>", value, "productinfo");
            return (Criteria) this;
        }

        public Criteria andProductinfoGreaterThan(String value) {
            addCriterion("productInfo >", value, "productinfo");
            return (Criteria) this;
        }

        public Criteria andProductinfoGreaterThanOrEqualTo(String value) {
            addCriterion("productInfo >=", value, "productinfo");
            return (Criteria) this;
        }

        public Criteria andProductinfoLessThan(String value) {
            addCriterion("productInfo <", value, "productinfo");
            return (Criteria) this;
        }

        public Criteria andProductinfoLessThanOrEqualTo(String value) {
            addCriterion("productInfo <=", value, "productinfo");
            return (Criteria) this;
        }

        public Criteria andProductinfoLike(String value) {
            addCriterion("productInfo like", value, "productinfo");
            return (Criteria) this;
        }

        public Criteria andProductinfoNotLike(String value) {
            addCriterion("productInfo not like", value, "productinfo");
            return (Criteria) this;
        }

        public Criteria andProductinfoIn(List<String> values) {
            addCriterion("productInfo in", values, "productinfo");
            return (Criteria) this;
        }

        public Criteria andProductinfoNotIn(List<String> values) {
            addCriterion("productInfo not in", values, "productinfo");
            return (Criteria) this;
        }

        public Criteria andProductinfoBetween(String value1, String value2) {
            addCriterion("productInfo between", value1, value2, "productinfo");
            return (Criteria) this;
        }

        public Criteria andProductinfoNotBetween(String value1, String value2) {
            addCriterion("productInfo not between", value1, value2, "productinfo");
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

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
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

        public Criteria andPacknumIsNull() {
            addCriterion("packNum is null");
            return (Criteria) this;
        }

        public Criteria andPacknumIsNotNull() {
            addCriterion("packNum is not null");
            return (Criteria) this;
        }

        public Criteria andPacknumEqualTo(Integer value) {
            addCriterion("packNum =", value, "packnum");
            return (Criteria) this;
        }

        public Criteria andPacknumNotEqualTo(Integer value) {
            addCriterion("packNum <>", value, "packnum");
            return (Criteria) this;
        }

        public Criteria andPacknumGreaterThan(Integer value) {
            addCriterion("packNum >", value, "packnum");
            return (Criteria) this;
        }

        public Criteria andPacknumGreaterThanOrEqualTo(Integer value) {
            addCriterion("packNum >=", value, "packnum");
            return (Criteria) this;
        }

        public Criteria andPacknumLessThan(Integer value) {
            addCriterion("packNum <", value, "packnum");
            return (Criteria) this;
        }

        public Criteria andPacknumLessThanOrEqualTo(Integer value) {
            addCriterion("packNum <=", value, "packnum");
            return (Criteria) this;
        }

        public Criteria andPacknumIn(List<Integer> values) {
            addCriterion("packNum in", values, "packnum");
            return (Criteria) this;
        }

        public Criteria andPacknumNotIn(List<Integer> values) {
            addCriterion("packNum not in", values, "packnum");
            return (Criteria) this;
        }

        public Criteria andPacknumBetween(Integer value1, Integer value2) {
            addCriterion("packNum between", value1, value2, "packnum");
            return (Criteria) this;
        }

        public Criteria andPacknumNotBetween(Integer value1, Integer value2) {
            addCriterion("packNum not between", value1, value2, "packnum");
            return (Criteria) this;
        }

        public Criteria andFeedbackinfoIsNull() {
            addCriterion("feedbackInfo is null");
            return (Criteria) this;
        }

        public Criteria andFeedbackinfoIsNotNull() {
            addCriterion("feedbackInfo is not null");
            return (Criteria) this;
        }

        public Criteria andFeedbackinfoEqualTo(String value) {
            addCriterion("feedbackInfo =", value, "feedbackinfo");
            return (Criteria) this;
        }

        public Criteria andFeedbackinfoNotEqualTo(String value) {
            addCriterion("feedbackInfo <>", value, "feedbackinfo");
            return (Criteria) this;
        }

        public Criteria andFeedbackinfoGreaterThan(String value) {
            addCriterion("feedbackInfo >", value, "feedbackinfo");
            return (Criteria) this;
        }

        public Criteria andFeedbackinfoGreaterThanOrEqualTo(String value) {
            addCriterion("feedbackInfo >=", value, "feedbackinfo");
            return (Criteria) this;
        }

        public Criteria andFeedbackinfoLessThan(String value) {
            addCriterion("feedbackInfo <", value, "feedbackinfo");
            return (Criteria) this;
        }

        public Criteria andFeedbackinfoLessThanOrEqualTo(String value) {
            addCriterion("feedbackInfo <=", value, "feedbackinfo");
            return (Criteria) this;
        }

        public Criteria andFeedbackinfoLike(String value) {
            addCriterion("feedbackInfo like", value, "feedbackinfo");
            return (Criteria) this;
        }

        public Criteria andFeedbackinfoNotLike(String value) {
            addCriterion("feedbackInfo not like", value, "feedbackinfo");
            return (Criteria) this;
        }

        public Criteria andFeedbackinfoIn(List<String> values) {
            addCriterion("feedbackInfo in", values, "feedbackinfo");
            return (Criteria) this;
        }

        public Criteria andFeedbackinfoNotIn(List<String> values) {
            addCriterion("feedbackInfo not in", values, "feedbackinfo");
            return (Criteria) this;
        }

        public Criteria andFeedbackinfoBetween(String value1, String value2) {
            addCriterion("feedbackInfo between", value1, value2, "feedbackinfo");
            return (Criteria) this;
        }

        public Criteria andFeedbackinfoNotBetween(String value1, String value2) {
            addCriterion("feedbackInfo not between", value1, value2, "feedbackinfo");
            return (Criteria) this;
        }

        public Criteria andFeedbackstatusIsNull() {
            addCriterion("feedbackStatus is null");
            return (Criteria) this;
        }

        public Criteria andFeedbackstatusIsNotNull() {
            addCriterion("feedbackStatus is not null");
            return (Criteria) this;
        }

        public Criteria andFeedbackstatusEqualTo(Integer value) {
            addCriterion("feedbackStatus =", value, "feedbackstatus");
            return (Criteria) this;
        }

        public Criteria andFeedbackstatusNotEqualTo(Integer value) {
            addCriterion("feedbackStatus <>", value, "feedbackstatus");
            return (Criteria) this;
        }

        public Criteria andFeedbackstatusGreaterThan(Integer value) {
            addCriterion("feedbackStatus >", value, "feedbackstatus");
            return (Criteria) this;
        }

        public Criteria andFeedbackstatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("feedbackStatus >=", value, "feedbackstatus");
            return (Criteria) this;
        }

        public Criteria andFeedbackstatusLessThan(Integer value) {
            addCriterion("feedbackStatus <", value, "feedbackstatus");
            return (Criteria) this;
        }

        public Criteria andFeedbackstatusLessThanOrEqualTo(Integer value) {
            addCriterion("feedbackStatus <=", value, "feedbackstatus");
            return (Criteria) this;
        }

        public Criteria andFeedbackstatusIn(List<Integer> values) {
            addCriterion("feedbackStatus in", values, "feedbackstatus");
            return (Criteria) this;
        }

        public Criteria andFeedbackstatusNotIn(List<Integer> values) {
            addCriterion("feedbackStatus not in", values, "feedbackstatus");
            return (Criteria) this;
        }

        public Criteria andFeedbackstatusBetween(Integer value1, Integer value2) {
            addCriterion("feedbackStatus between", value1, value2, "feedbackstatus");
            return (Criteria) this;
        }

        public Criteria andFeedbackstatusNotBetween(Integer value1, Integer value2) {
            addCriterion("feedbackStatus not between", value1, value2, "feedbackstatus");
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

        public Criteria andBacktimeIsNull() {
            addCriterion("backTime is null");
            return (Criteria) this;
        }

        public Criteria andBacktimeIsNotNull() {
            addCriterion("backTime is not null");
            return (Criteria) this;
        }

        public Criteria andBacktimeEqualTo(Date value) {
            addCriterion("backTime =", value, "backtime");
            return (Criteria) this;
        }

        public Criteria andBacktimeNotEqualTo(Date value) {
            addCriterion("backTime <>", value, "backtime");
            return (Criteria) this;
        }

        public Criteria andBacktimeGreaterThan(Date value) {
            addCriterion("backTime >", value, "backtime");
            return (Criteria) this;
        }

        public Criteria andBacktimeGreaterThanOrEqualTo(Date value) {
            addCriterion("backTime >=", value, "backtime");
            return (Criteria) this;
        }

        public Criteria andBacktimeLessThan(Date value) {
            addCriterion("backTime <", value, "backtime");
            return (Criteria) this;
        }

        public Criteria andBacktimeLessThanOrEqualTo(Date value) {
            addCriterion("backTime <=", value, "backtime");
            return (Criteria) this;
        }

        public Criteria andBacktimeIn(List<Date> values) {
            addCriterion("backTime in", values, "backtime");
            return (Criteria) this;
        }

        public Criteria andBacktimeNotIn(List<Date> values) {
            addCriterion("backTime not in", values, "backtime");
            return (Criteria) this;
        }

        public Criteria andBacktimeBetween(Date value1, Date value2) {
            addCriterion("backTime between", value1, value2, "backtime");
            return (Criteria) this;
        }

        public Criteria andBacktimeNotBetween(Date value1, Date value2) {
            addCriterion("backTime not between", value1, value2, "backtime");
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