# Whistleblower App Backend

This project is a Spring Boot-based backend for the Whistleblower App, designed to securely collect, store, and process complaints from users. It provides RESTful APIs for submitting complaints with file attachments and includes a health check endpoint. The backend is production-ready and can be accessed via the provided base URL without any local configuration.

## Base URL

```
http://awaaz-backend-alb-820503857.eu-north-1.elb.amazonaws.com
```

## API Endpoints

### 1. Submit a Complaint
- **Endpoint:** `POST /api/complaints`
- **Description:** Submit a new complaint with description and file attachments.
- **Request:**
  - `multipart/form-data` with fields:
    - `description`: string
    - `files`: one or more files
- **Response:**
  - `200 OK` with a message and uploaded file URLs.

### 2. Health Check
- **Endpoint:** `GET /api/complaints/check`
- **Description:** Check if the complaint service is running.
- **Response:**
  - `200 OK` with a status message.

---

## Notes
- All endpoints are accessible via the base URL above.
- For file uploads, use `multipart/form-data`.
- Authentication is not required for complaint submission.
- For more details, refer to the source code or contact the maintainers.

## Contributing
- You are encouraged to build something with this backend or contribute to its development! Feel free to fork the repository, submit issues, or open pull requests.
