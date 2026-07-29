# Flowable Core Concepts & Practical Guide

This document introduces the core concepts of the Flowable process engine, key service interfaces, and the most commonly used historical approval tables in business scenarios.

## 1. Core Concepts

*   **Process Definition:** Think of this as a "class definition" or a "template." It refers to the process diagram designed in the BPMN 2.0 modeler, which becomes a data model once deployed to Flowable. For example: "Leave Request Process V1.0".
*   **Process Instance:** Equivalent to an "object instantiation." When a user initiates a "Leave Request Process," the system creates a corresponding process instance. A single process definition can spawn thousands of process instances (e.g., John's leave request, Jane's leave request).
*   **Task:** A node (specifically a UserTask) in the process flow that requires human intervention. For example, "Department Manager Approval" or "Financial Review."
*   **Execution:** The thread of execution Flowable uses to describe how a process is running. If a process follows a single path, the execution and process instance have a one-to-one relationship. If a parallel gateway is encountered (e.g., distributing a task to multiple people simultaneously), a process instance will generate multiple child executions.
*   **Variables:** Data passed during the process execution (e.g., `makerId`, `checkerId`, `amount`). They are used to control the process flow (via conditional gateways) or record system snapshots of business data.

## 2. Core Service Interfaces

Flowable utilizes a classic server-side Service architecture. Developers interact with the engine core by injecting different Services.

### 🟢 RuntimeService
**Responsibility:** Manages all **currently running** process instances and executions.
**Primary Uses:**
*   Start a new process instance based on a process definition (Key): `runtimeService.startProcessInstanceByKey("paymentCheckProcess")`
*   Query information about currently running process instances.
*   Dynamically set or retrieve process variables while a process is running.

### 🔵 TaskService
**Responsibility:** Manages all active tasks that require **human processing**.
**Primary Uses:**
*   **Query To-Do Lists:** Find tasks currently assigned to a specific approver (e.g., tasks pending for Maker, reviews pending for Checker): `taskService.createTaskQuery().taskAssignee("userId").list()`
*   **Complete Tasks:** When an approver clicks "Approve" or "Reject": `taskService.complete(taskId)`
*   **Task Transfer/Delegation:** Temporarily hand over a task to someone else.
*   **Add Approval Comments:** Write a review comment before calling complete: `taskService.addComment(taskId, processInstanceId, "Payment approved, amount verified.")`

### 🟠 HistoryService
**Responsibility:** Manages all **completed** process, node, task, and variable data. This is the core component for implementing an "Audit Trail."
**Primary Uses:**
*   **Query Completed Tasks:** Find all historical tasks a specific user has participated in.
*   **Query Process Trajectory:** Extract the complete timeline for a specific ticket (Who initiated -> Who did the 1st review -> Who did the 2nd review).
*   **Data Statistics & Analysis:** Calculate approval turnaround times and identify bottleneck stages based on historical data.

*(Note: **RepositoryService** is a foundational service primarily responsible for reading the designed XML process diagrams and deploying them to the database.)*

---

## 3. Historical Approval Tables Relevant to Business Users

Flowable contains dozens of database tables, but for end business users (like auditors, financial directors, or operations managers), the most valuable data for business traceability is concentrated in the following historical tables starting with `ACT_HI_`:

### 1. Business Ledger Table: `ACT_HI_PROCINST` (Historic Process Instance Table)
*   **Business Significance:** A high-level overview of tickets. One record represents the complete lifecycle of a work order from start to finish.
*   **Key Fields:**
    *   `START_TIME_`: When the process was initiated.
    *   `END_TIME_`: When the process ended (if not null, it means the process is fully complete).
    *   `START_USER_ID_`: The initiator (the Maker).
*   **Business Scenario:** *"Pull a report on how many large-sum transfer approvals were initiated last month and the average time taken per approval."*

### 2. Approval Trajectory Table: `ACT_HI_TASKINST` (Historic Task Instance Table)
*   **Business Significance:** **The most critical audit table!** It faithfully records the specific processing details of **every manual node** in the business process.
*   **Key Fields:**
    *   `NAME_`: Task name (e.g., "Financial Director Review").
    *   `ASSIGNEE_`: The actual person who handled it (the Checker).
    *   `START_TIME_` / `END_TIME_`: When the task arrived at the node and when it was completed.
    *   `DURATION_`: The time spent at this node (in milliseconds).
*   **Business Scenario:** *"For this anomalous payment, who exactly did the final review? On what day and at what exact minute did they approve it? How many days was this ticket stuck in the compliance department?"*

### 3. Approval Comments Table: `ACT_HI_COMMENT` (Historic Comment Table)
*   **Business Significance:** Records the text comments manually entered by approvers when processing a task (reasons for approval/rejection).
*   **Key Fields:**
    *   `USER_ID_`: The person who wrote the comment.
    *   `MESSAGE_`: The body of the comment.
*   **Business Scenario:** *"This reimbursement was kicked back by legal last week; what was the specific reason for rejection?"*

### 4. Historical Snapshot Table: `ACT_HI_VARINST` (Historic Variable Instance Table)
*   **Business Significance:** Records **snapshots of key business data** at the time the process was executing.
*   **Key Fields:**
    *   `NAME_`: Variable name (e.g., `payment_amount`).
    *   `TEXT_` / `DOUBLE_`: The specific value of the variable.
*   **Business Scenario:** *Essential for anti-tampering audits. If the amount in a business form changed due to a system bug or malicious modification, auditors can verify using this table: "When this ticket was originally routed for approval, what was the secure amount recorded in the process engine?"*
