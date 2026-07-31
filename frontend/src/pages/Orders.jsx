import orders from "../data/orders";

function Orders() {
  return (
    <main className="page-container">
      <div className="page-heading">
        <h1>Your orders</h1>
        <p>View current and previous grocery orders.</p>
      </div>

      <section className="orders-list">
        {orders.map((order) => (
          <article className="order-card" key={order.id}>
            <div className="order-card-header">
              <div>
                <p className="small-label">Order number</p>
                <h2>{order.id}</h2>
              </div>

              <span
                className={`order-status ${order.status
                  .toLowerCase()
                  .replace(" ", "-")}`}
              >
                {order.status}
              </span>
            </div>

            <div className="order-details">
              <div>
                <p className="small-label">Order date</p>
                <p>{order.date}</p>
              </div>

              <div>
                <p className="small-label">Items</p>
                <p>{order.items}</p>
              </div>

              <div>
                <p className="small-label">Total</p>
                <p>${order.total.toFixed(2)}</p>
              </div>

              <div>
                <p className="small-label">Delivery</p>
                <p>{order.estimatedDelivery}</p>
              </div>
            </div>

            <button className="secondary-button" type="button">
              View order details
            </button>
          </article>
        ))}
      </section>
    </main>
  );
}

export default Orders;