function SearchBar({ searchTerm, onSearchChange }) {
  return (
    <div className="search-container">
      <label className="visually-hidden" htmlFor="product-search">
        Search products
      </label>

      <input
        id="product-search"
        className="search-input"
        type="search"
        placeholder="Search for groceries..."
        value={searchTerm}
        onChange={(event) => onSearchChange(event.target.value)}
      />
    </div>
  );
}

export default SearchBar;