import {
    ApiError,
    createStudent,
    deleteStudent,
    getStudents,
    updateStudent
} from "./api.js";

const form = document.querySelector("#student-form");
const formTitle = document.querySelector("#form-title");
const formDescription = document.querySelector("#form-description");
const formAlert = document.querySelector("#form-alert");
const submitButton = document.querySelector("#submit-button");
const cancelEditButton = document.querySelector("#cancel-edit-button");
const refreshButton = document.querySelector("#refresh-button");
const studentsSummary = document.querySelector("#students-summary");
const tableAlert = document.querySelector("#table-alert");
const tableBody = document.querySelector("#students-table-body");

const fields = {
    firstName: document.querySelector("#firstName"),
    surname: document.querySelector("#surname"),
    emailAddress: document.querySelector("#emailAddress"),
    contactNumber: document.querySelector("#contactNumber"),
    averageMark: document.querySelector("#averageMark")
};

let studentsById = new Map();
let editingStudentId = null;

form.addEventListener("submit", handleSubmit);
cancelEditButton.addEventListener("click", resetForm);
refreshButton.addEventListener("click", loadStudents);
tableBody.addEventListener("click", handleTableAction);

loadStudents();

async function loadStudents() {
    clearAlert(tableAlert);
    setRefreshLoading(true);
    studentsSummary.textContent = "Loading student records…";

    try {
        const students = await getStudents();
        studentsById = new Map(students.map((student) => [student.id, student]));
        renderStudents(students);
        studentsSummary.textContent = `${students.length} student${students.length === 1 ? "" : "s"} found`;
    } catch (error) {
        studentsSummary.textContent = "Unable to load students";
        showAlert(tableAlert, error.message);
        renderEmptyState("Student records could not be loaded.");
    } finally {
        setRefreshLoading(false);
    }
}

async function handleSubmit(event) {
    event.preventDefault();
    clearFormErrors();
    clearAlert(formAlert);

    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    const student = getFormData();
    setSubmitLoading(true);

    try {
        if (editingStudentId === null) {
            await createStudent(student);
            resetForm();
        } else {
            await updateStudent(editingStudentId, student);
            resetForm();
        }

        await loadStudents();
    } catch (error) {
        if (error instanceof ApiError) {
            showFieldErrors(error.fieldErrors);
        }

        showAlert(formAlert, error.message);
    } finally {
        setSubmitLoading(false);
    }
}

async function handleTableAction(event) {
    const actionButton = event.target.closest("button[data-action]");

    if (!actionButton) {
        return;
    }

    const studentId = Number(actionButton.dataset.studentId);

    if (actionButton.dataset.action === "edit") {
        beginEdit(studentId);
        return;
    }

    if (actionButton.dataset.action === "delete") {
        await removeStudent(studentId);
    }
}

function beginEdit(studentId) {
    const student = studentsById.get(studentId);

    if (!student) {
        showAlert(tableAlert, "The selected student is no longer available. Refresh the list and try again.");
        return;
    }

    editingStudentId = student.id;
    fields.firstName.value = student.name ?? "";
    fields.surname.value = student.surname ?? "";
    fields.emailAddress.value = student.email_address ?? "";
    fields.contactNumber.value = student.contact_number ?? "";
    fields.averageMark.value = student.averageMark ?? "";

    formTitle.textContent = `Edit ${student.name} ${student.surname}`;
    formDescription.textContent = "Update the student details, then save the changes.";
    submitButton.textContent = "Save changes";
    cancelEditButton.classList.remove("is-hidden");
    clearFormErrors();
    clearAlert(formAlert);
    fields.firstName.focus();
}

function resetForm() {
    editingStudentId = null;
    form.reset();
    formTitle.textContent = "Add a student";
    formDescription.textContent = "Complete the details below to create a new student record.";
    submitButton.textContent = "Create student";
    cancelEditButton.classList.add("is-hidden");
    clearFormErrors();
    clearAlert(formAlert);
}

async function removeStudent(studentId) {
    const student = studentsById.get(studentId);
    const studentName = student ? `${student.name} ${student.surname}` : "this student";

    if (!window.confirm(`Delete ${studentName}? This action cannot be undone.`)) {
        return;
    }

    clearAlert(tableAlert);

    try {
        await deleteStudent(studentId);

        if (editingStudentId === studentId) {
            resetForm();
        }

        await loadStudents();
    } catch (error) {
        showAlert(tableAlert, error.message);
    }
}

function getFormData() {
    return {
        name: fields.firstName.value.trim(),
        surname: fields.surname.value.trim(),
        email_address: fields.emailAddress.value.trim() || null,
        contact_number: fields.contactNumber.value.trim() || null,
        averageMark: Number(fields.averageMark.value)
    };
}

function renderStudents(students) {
    tableBody.replaceChildren();

    if (students.length === 0) {
        renderEmptyState("No students have been created yet.");
        return;
    }

    for (const student of students) {
        const row = document.createElement("tr");
        row.append(
            createCell(student.id),
            createStudentCell(student),
            createContactCell(student),
            createCell(formatMark(student.averageMark)),
            createCell(formatDate(student.date_of_enrollment)),
            createStatusCell(student.status),
            createActionsCell(student.id)
        );
        tableBody.append(row);
    }
}

function renderEmptyState(message) {
    tableBody.replaceChildren();
    const row = document.createElement("tr");
    const cell = document.createElement("td");
    cell.colSpan = 7;
    cell.className = "empty-state";
    cell.textContent = message;
    row.append(cell);
    tableBody.append(row);
}

function createCell(value) {
    const cell = document.createElement("td");
    cell.textContent = value ?? "—";
    return cell;
}

function createStudentCell(student) {
    const cell = document.createElement("td");
    const name = document.createElement("span");
    const email = document.createElement("span");

    name.className = "student-name";
    name.textContent = `${student.name ?? ""} ${student.surname ?? ""}`.trim() || "—";
    email.className = "student-email";
    email.textContent = student.email_address || "No email supplied";

    cell.append(name, email);
    return cell;
}

function createContactCell(student) {
    const cell = document.createElement("td");
    cell.textContent = student.contact_number || "—";
    return cell;
}

function createStatusCell(status) {
    const cell = document.createElement("td");
    const badge = document.createElement("span");

    badge.className = `status-badge ${status ? "status-passing" : "status-not-passing"}`;
    badge.textContent = status ? "Passing" : "Not passing";

    cell.append(badge);
    return cell;
}

function createActionsCell(studentId) {
    const cell = document.createElement("td");
    const actionGroup = document.createElement("div");
    const editButton = document.createElement("button");
    const deleteButton = document.createElement("button");

    actionGroup.className = "action-group";

    editButton.type = "button";
    editButton.className = "button button-secondary button-small";
    editButton.dataset.action = "edit";
    editButton.dataset.studentId = String(studentId);
    editButton.textContent = "Edit";

    deleteButton.type = "button";
    deleteButton.className = "button button-danger button-small";
    deleteButton.dataset.action = "delete";
    deleteButton.dataset.studentId = String(studentId);
    deleteButton.textContent = "Delete";

    actionGroup.append(editButton, deleteButton);
    cell.append(actionGroup);
    return cell;
}

function showFieldErrors(fieldErrors) {
    const fieldNameMap = {
        firstName: "firstName",
        surname: "surname",
        emailAddress: "emailAddress",
        contactNumber: "contactNumber",
        averageMark: "averageMark"
    };

    for (const [backendField, message] of Object.entries(fieldErrors)) {
        const fieldName = fieldNameMap[backendField];

        if (!fieldName) {
            continue;
        }

        const input = fields[fieldName];
        const errorElement = document.querySelector(`#${fieldName}-error`);
        input.setAttribute("aria-invalid", "true");
        errorElement.textContent = message;
    }
}

function clearFormErrors() {
    for (const fieldName of Object.keys(fields)) {
        fields[fieldName].removeAttribute("aria-invalid");
        document.querySelector(`#${fieldName}-error`).textContent = "";
    }
}

function showAlert(alertElement, message) {
    alertElement.textContent = message;
    alertElement.classList.remove("is-hidden");
}

function clearAlert(alertElement) {
    alertElement.textContent = "";
    alertElement.classList.add("is-hidden");
}

function setSubmitLoading(isLoading) {
    submitButton.disabled = isLoading;
    submitButton.textContent = isLoading ? "Saving…" : editingStudentId === null ? "Create student" : "Save changes";
}

function setRefreshLoading(isLoading) {
    refreshButton.disabled = isLoading;
    refreshButton.textContent = isLoading ? "Refreshing…" : "Refresh";
}

function formatMark(mark) {
    const numericMark = Number(mark);
    return Number.isFinite(numericMark) ? numericMark.toFixed(2) : "—";
}

function formatDate(date) {
    if (!date) {
        return "—";
    }

    return new Intl.DateTimeFormat("en-ZA", {
        day: "2-digit",
        month: "short",
        year: "numeric"
    }).format(new Date(`${date}T00:00:00`));
}
