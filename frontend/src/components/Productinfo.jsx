function ProductInfo({ product, addToCart }) {
  return (
    <article className="product-card">
      <div className="product-image-container">
        <img
          className="product-image"
          src={product.image}
          alt={product.name}
        />
      </div>

      <div className="product-card-content">
        <p className="product-category">{product.category}</p>
        <h2 className="product-name">{product.name}</h2>
        <p className="product-description">{product.description}</p>

        <div className="product-card-footer">
          <p className="product-price">${product.price.toFixed(2)}</p>

          <button
            className="primary-button"
            type="button"
            onClick={() => addToCart(product)}
          >
            Add to cart
          </button>
        </div>
      </div>
    </article>
  );
}

export default ProductInfo;