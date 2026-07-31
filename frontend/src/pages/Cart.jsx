function Cart({ cartItems, removeFromCart, updateQuantity }) {
  const subtotal = cartItems.reduce(
    (total, item) => total + item.price * item.quantity,
    0
  );

  return (
    <main className="page-container">
      <h1>Your cart</h1>

      {cartItems.length === 0 ? (
        <div className="empty-message">
          <h2>Your cart is empty</h2>
          <p>Add products from the home page to begin your order.</p>
        </div>
      ) : (
        <div className="cart-layout">
          <section className="cart-items">
            {cartItems.map((item) => (
              <article className="cart-item" key={item.id}>
                <img
                  className="cart-item-image"
                  src={item.image}
                  alt={item.name}
                />

                <div className="cart-item-information">
                  <h2>{item.name}</h2>
                  <p>${item.price.toFixed(2)} each</p>

                  <label htmlFor={`quantity-${item.id}`}>
                    Quantity
                  </label>

                  <input
                    id={`quantity-${item.id}`}
                    className="quantity-input"
                    type="number"
                    min="1"
                    value={item.quantity}
                    onChange={(event) =>
                      updateQuantity(
                        item.id,
                        Number(event.target.value)
                      )
                    }
                  />
                </div>

                <div className="cart-item-actions">
                  <p className="cart-item-total">
                    ${(item.price * item.quantity).toFixed(2)}
                  </p>

                  <button
                    className="text-button"
                    type="button"
                    onClick={() => removeFromCart(item.id)}
                  >
                    Remove
                  </button>
                </div>
              </article>
            ))}
          </section>

          <aside className="order-summary">
            <h2>Order summary</h2>

            <div className="summary-row">
              <span>Subtotal</span>
              <span>${subtotal.toFixed(2)}</span>
            </div>

            <div className="summary-row">
              <span>Delivery</span>
              <span>Calculated later</span>
            </div>

            <div className="summary-row summary-total">
              <span>Total</span>
              <span>${subtotal.toFixed(2)}</span>
            </div>

            <button className="primary-button full-button" type="button">
              Continue to checkout
            </button>
          </aside>
        </div>
      )}
    </main>
  );
}

export default Cart;