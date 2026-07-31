import { useState } from "react";

function AccountPage() {
  const savedUser = JSON.parse(localStorage.getItem("currentUser"));

  const [currentUser, setCurrentUser] = useState(savedUser);
  const [editingInformation, setEditingInformation] = useState(false);
  const [editingAddress, setEditingAddress] = useState(false);

  const [firstName, setFirstName] = useState(
    savedUser?.firstName || ""
  );

  const [lastName, setLastName] = useState(
    savedUser?.lastName || ""
  );

  const [email, setEmail] = useState(
    savedUser?.email || ""
  );

  const [deliveryAddress, setDeliveryAddress] = useState(
    savedUser?.deliveryAddress || ""
  );

  if (!currentUser) {
    return (
      <main className="page-container">
        <div className="page-heading">
          <h1>Your account</h1>
          <p>Please log in to view your account information.</p>
        </div>
      </main>
    );
  }

  function saveUser(updatedUser) {
    localStorage.setItem(
      "currentUser",
      JSON.stringify(updatedUser)
    );

    setCurrentUser(updatedUser);
  }

  function handleInformationSave(event) {
    event.preventDefault();

    const updatedUser = {
      ...currentUser,
      firstName,
      lastName,
      email,
    };

    saveUser(updatedUser);
    setEditingInformation(false);
  }

  function handleAddressSave(event) {
    event.preventDefault();

    const updatedUser = {
      ...currentUser,
      deliveryAddress,
    };

    saveUser(updatedUser);
    setEditingAddress(false);
  }

  return (
    <main className="page-container">
      <div className="page-heading">
        <h1>Your account</h1>
        <p>Manage your personal and delivery information.</p>
      </div>

      <section className="account-grid">
        <article className="information-card">
          <h2>Personal information</h2>

          {editingInformation ? (
            <form onSubmit={handleInformationSave}>
              <label className="small-label" htmlFor="firstName">
                First name
              </label>

              <input
                id="firstName"
                type="text"
                value={firstName}
                onChange={(event) =>
                  setFirstName(event.target.value)
                }
                required
              />

              <label className="small-label" htmlFor="lastName">
                Last name
              </label>

              <input
                id="lastName"
                type="text"
                value={lastName}
                onChange={(event) =>
                  setLastName(event.target.value)
                }
                required
              />

              <label className="small-label" htmlFor="email">
                Email
              </label>

              <input
                id="email"
                type="email"
                value={email}
                onChange={(event) =>
                  setEmail(event.target.value)
                }
                required
              />

              <button className="primary-button" type="submit">
                Save information
              </button>

              <button
                className="secondary-button"
                type="button"
                onClick={() => setEditingInformation(false)}
              >
                Cancel
              </button>
            </form>
          ) : (
            <>
              <p className="small-label">Name</p>
              <p>
                {currentUser.firstName} {currentUser.lastName}
              </p>

              <p className="small-label">Email</p>
              <p>{currentUser.email}</p>

              <button
                className="secondary-button"
                type="button"
                onClick={() => setEditingInformation(true)}
              >
                Edit information
              </button>
            </>
          )}
        </article>

        <article className="information-card">
          <h2>Delivery address</h2>

          {editingAddress ? (
            <form onSubmit={handleAddressSave}>
              <label className="small-label" htmlFor="deliveryAddress">
                Address
              </label>

              <textarea
                id="deliveryAddress"
                value={deliveryAddress}
                onChange={(event) =>
                  setDeliveryAddress(event.target.value)
                }
                required
              />

              <button className="primary-button" type="submit">
                Save address
              </button>

              <button
                className="secondary-button"
                type="button"
                onClick={() => setEditingAddress(false)}
              >
                Cancel
              </button>
            </form>
          ) : (
            <>
              <p>
                {currentUser.deliveryAddress ||
                  "No delivery address saved"}
              </p>

              <button
                className="secondary-button"
                type="button"
                onClick={() => setEditingAddress(true)}
              >
                Edit address
              </button>
            </>
          )}
        </article>
      </section>
    </main>
  );
}

export default AccountPage;