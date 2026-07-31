import { useState } from "react";

import ProductInfo from "../components/ProductInfo";
import SearchBar from "../components/SearchBar";
import Sort from "../components/Sort";
import products from "../data/products";

function Home({ addToCart }) {
  const [searchTerm, setSearchTerm] = useState("");
  const [sortOption, setSortOption] = useState("default");

  const filteredProducts = products.filter((product) => {
    const searchableText = `${product.name} ${product.category}`.toLowerCase();

    return searchableText.includes(searchTerm.toLowerCase());
  });

  const sortedProducts = [...filteredProducts].sort((first, second) => {
    switch (sortOption) {
      case "name-ascending":
        return first.name.localeCompare(second.name);

      case "name-descending":
        return second.name.localeCompare(first.name);

      case "price-low":
        return first.price - second.price;

      case "price-high":
        return second.price - first.price;

      default:
        return 0;
    }
  });

  return (
    <main className="home-page">

      <section className="home-controls" aria-label="Product controls">
        <SearchBar
          searchTerm={searchTerm}
          onSearchChange={setSearchTerm}
        />

        <Sort
          sortOption={sortOption}
          onSortChange={setSortOption}
        />
      </section>

      <section className="products-section">
        <div className="section-heading">
          <h2>Shop products</h2>
          <p>
            {sortedProducts.length}{" "}
            {sortedProducts.length === 1 ? "product" : "products"}
          </p>
        </div>

        {sortedProducts.length > 0 ? (
          <div className="product-grid">
            {sortedProducts.map((product) => (
              <ProductInfo
                key={product.id}
                product={product}
                addToCart={addToCart}
              />
            ))}
          </div>
        ) : (
          <div className="empty-message">
            <h2>No products found</h2>
            <p>Try searching for a different product.</p>
          </div>
        )}
      </section>
    </main>
  );
}

export default Home;