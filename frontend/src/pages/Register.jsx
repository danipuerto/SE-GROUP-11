import { useState } from "react";
import { Link, useNavigate } from "react-router";

function Register() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const [message, setMessage] = useState("");

  function handleChange(event) {
    const { name, value } = event.target;

    setFormData((currentData) => ({
      ...currentData,
      [name]: value,
    }));
  }

  function handleSubmit(event) {
  event.preventDefault();

  const hasEmptyField = Object.values(formData).some(
    (value) => value.trim() === ""
  );

  if (hasEmptyField) {
    setMessage("Please complete every field.");
    return;
  }

  if (formData.password !== formData.confirmPassword) {
    setMessage("The passwords do not match.");
    return;
  }

  const existingUsers =
    JSON.parse(localStorage.getItem("users")) || [];

  const accountAlreadyExists = existingUsers.some(
    (user) =>
      user.email.toLowerCase() === formData.email.toLowerCase()
  );

  if (accountAlreadyExists) {
    setMessage("An account with this email already exists.");
    return;
  }

  const newUser = {
    id: Date.now(),
    firstName: formData.firstName,
    lastName: formData.lastName,
    email: formData.email,
    password: formData.password,
    deliveryAddress: "",
  };

  const updatedUsers = [...existingUsers, newUser];

  localStorage.setItem("users", JSON.stringify(updatedUsers));

  setMessage("");
  navigate("/login");
}

  return (
    <main className="form-page">
      <section className="form-card">
        <h1>Create an account</h1>
        <p>Register to place orders and manage your profile.</p>

        <form onSubmit={handleSubmit}>
          <div className="name-fields">
            <div className="form-group">
              <label htmlFor="first-name">First name</label>
              <input
                id="first-name"
                name="firstName"
                type="text"
                value={formData.firstName}
                onChange={handleChange}
                autoComplete="given-name"
              />
            </div>

            <div className="form-group">
              <label htmlFor="last-name">Last name</label>
              <input
                id="last-name"
                name="lastName"
                type="text"
                value={formData.lastName}
                onChange={handleChange}
                autoComplete="family-name"
              />
            </div>
          </div>

          <div className="form-group">
            <label htmlFor="register-email">Email address</label>
            <input
              id="register-email"
              name="email"
              type="email"
              value={formData.email}
              onChange={handleChange}
              autoComplete="email"
            />
          </div>

          <div className="form-group">
            <label htmlFor="register-password">Password</label>
            <input
              id="register-password"
              name="password"
              type="password"
              value={formData.password}
              onChange={handleChange}
              autoComplete="new-password"
            />
          </div>

          <div className="form-group">
            <label htmlFor="confirm-password">Confirm password</label>
            <input
              id="confirm-password"
              name="confirmPassword"
              type="password"
              value={formData.confirmPassword}
              onChange={handleChange}
              autoComplete="new-password"
            />
          </div>

          {message && <p className="form-message">{message}</p>}

          <button className="primary-button full-button" type="submit">
            Create account
          </button>
        </form>

        <p className="form-footer">
          Already registered? <Link to="/login">Log in</Link>
        </p>
      </section>
    </main>
  );
}

export default Register;