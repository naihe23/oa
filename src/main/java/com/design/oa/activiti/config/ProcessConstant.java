package com.design.oa.activiti.config;

public interface ProcessConstant {
    // region **************** 流程配置相关 ****************
    // /** * 设置Activiti是否开启跳过表达式 */
    String SKIP_EXPRESSION = "_ACTIVITI_SKIP_EXPRESSION_ENABLED";
    /** * 排他网关 */
    String EXCLUSIVE_GATEWAY = "exclusiveGateway";
    /** * 启动节点 */
    String START_EVENT = "startEvent";
    // endregion
    // region **************** 流程变量相关 - ProcessVariablesVO ∆∆ 此标志为该属性为必填属性 ****************
    // /** * 下个任务办理人 记录人员编号 ∆∆ */
    String DEAL_PERSON_ID = "deal_person";
    /** * 下个任务办理人姓名 */
    String DEAL_PERSON_NAME = "deal_person_name";
    /** * 流程发起人 记录人员编号 ∆∆ */
    String PROCESS_START_PERSON = "start_person";
    /** * 流程发起人姓名 */
    String PROCESS_START_PERSON_NAME = "start_person_name";

    String REJECT_REASON = "reject_reason";

    String CURRENT_PERSON_ID = "current_person_id";

    String CURRENT_PERSON_NAME = "current_person_name";

}
