import { useState } from "react";
import { Link, useNavigate } from "react-router";

function Login() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: "",
    password: "",
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

  if (!formData.email.trim() || !formData.password.trim()) {
    setMessage("Please enter your email and password.");
    return;
  }

  const users =
    JSON.parse(localStorage.getItem("users")) || [];

  const matchingUser = users.find(
    (user) =>
      user.email.toLowerCase() ===
        formData.email.toLowerCase() &&
      user.password === formData.password
  );

  if (!matchingUser) {
    setMessage("Incorrect email or password.");
    return;
  }

  localStorage.setItem(
    "currentUser",
    JSON.stringify(matchingUser)
  );

  setMessage("");
  navigate("/account");
}

  return (
    <main className="form-page">
      <section className="form-card">
        <h1>Welcome back</h1>
        <p>Log in to view your account and orders.</p>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="login-email">Email address</label>
            <input
              id="login-email"
              name="email"
              type="email"
              value={formData.email}
              onChange={handleChange}
              autoComplete="email"
            />
          </div>

          <div className="form-group">
            <label htmlFor="login-password">Password</label>
            <input
              id="login-password"
              name="password"
              type="password"
              value={formData.password}
              onChange={handleChange}
              autoComplete="current-password"
            />
          </div>

          {message && <p className="form-message">{message}</p>}

          <button className="primary-button full-button" type="submit">
            Log in
          </button>
        </form>

        <p className="form-footer">
          Do not have an account?{" "}
          <Link to="/register">Create one</Link>
        </p>
      </section>
    </main>
  );
}

export default Login;