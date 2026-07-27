const STUDENTS_URL = "/students";
const STUDENT_URL = "/student";

export async function getStudents() {
    return request(STUDENTS_URL);
}

export async function createStudent(student) {
    return request(STUDENTS_URL, {
        method: "POST",
        body: student
    });
}

export async function updateStudent(studentId, student) {
    return request(`${STUDENT_URL}/${studentId}`, {
        method: "PUT",
        body: student
    });
}

export async function deleteStudent(studentId) {
    return request(`${STUDENT_URL}/${studentId}`, {
        method: "DELETE"
    });
}

async function request(url, options = {}) {
    const response = await fetch(url, {
        method: options.method ?? "GET",
        headers: {
            "Accept": "application/json",
            ...(options.body ? {"Content-Type": "application/json"} : {})
        },
        body: options.body ? JSON.stringify(options.body) : undefined
    });

    if (response.ok) {
        return response.status === 204 ? null : response.json();
    }

    let errorBody = null;

    try {
        errorBody = await response.json();
    } catch {
        // The server did not return JSON; use the HTTP status below.
    }

    throw new ApiError(
        errorBody?.message ?? `Request failed with status ${response.status}.`,
        errorBody?.fieldErrors ?? {}
    );
}

export class ApiError extends Error {
    constructor(message, fieldErrors) {
        super(message);
        this.name = "ApiError";
        this.fieldErrors = fieldErrors;
    }
}
