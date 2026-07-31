import { NavLink } from "react-router";
import Account from "./Account";

function Navbar({ cartCount }) {
  return (
    <header className="navbar">
      <nav className="navbar-content" aria-label="Main navigation">
        <NavLink className="navbar-brand" to="/">
          Baking Supply
        </NavLink>

        <div className="navbar-links">
          <NavLink
            className={({ isActive }) =>
              isActive ? "nav-link active-link" : "nav-link"
            }
            to="/"
          >
            Home
          </NavLink>

          <NavLink
            className={({ isActive }) =>
              isActive ? "nav-link active-link" : "nav-link"
            }
            to="/orders"
          >
            Orders
          </NavLink>

          <Account />

          <NavLink className="cart-link" to="/cart">
            Cart
            {cartCount > 0 && (
              <span className="cart-count">{cartCount}</span>
            )}
          </NavLink>
        </div>
      </nav>
    </header>
  );
}

export default Navbar;