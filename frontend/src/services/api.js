const API_BASE_URL = "http://localhost:8080/api";

async function request(endpoint, options = {}) {
  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    headers: {
      "Content-Type": "application/json",
      ...options.headers,
    },
    ...options,
  });

  if (!response.ok) {
    throw new Error(`Request failed with status ${response.status}`);
  }

  if (response.status === 204) {
    return null;
  }

  return response.json();
}

export function getProducts() {
  return request("/products");
}

export function loginCustomer(credentials) {
  return request("/customers/login", {
    method: "POST",
    body: JSON.stringify(credentials),
  });
}

export function registerCustomer(customer) {
  return request("/customers/register", {
    method: "POST",
    body: JSON.stringify(customer),
  });
}

export function getCustomerOrders(customerId) {
  return request(`/customers/${customerId}/orders`);
}