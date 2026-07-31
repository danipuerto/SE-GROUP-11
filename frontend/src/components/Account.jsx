import { Link } from "react-router";

function Account() {
  return (
    <div className="account-links">
      <Link className="nav-link" to="/account">
        Account
      </Link>

      <Link className="login-link" to="/login">
        Log in
      </Link>
    </div>
  );
}

export default Account;