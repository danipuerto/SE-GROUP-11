function Sort({ sortOption, onSortChange }) {
  return (
    <div className="sort-container">
      <label className="sort-label" htmlFor="product-sort">
        Sort by
      </label>

      <select
        id="product-sort"
        className="sort-select"
        value={sortOption}
        onChange={(event) => onSortChange(event.target.value)}
      >
        <option value="default">Featured</option>
        <option value="name-ascending">Name: A–Z</option>
        <option value="name-descending">Name: Z–A</option>
        <option value="price-low">Price: Low to High</option>
        <option value="price-high">Price: High to Low</option>
      </select>
    </div>
  );
}

export default Sort;