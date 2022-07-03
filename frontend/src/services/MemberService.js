

export default async function getAllMembersPayments() {
  const response = await fetch("http://localhost:8083/api/v1/members/all-payments");
  if (response.status === 200) {
      let body = await response.json();
      return body;
  } else {
      return null;
  }
}

export async function getAllPayments() {
  const response = await fetch("http://localhost:8083/api/v1/payments");
  if (response.status === 200) {
      let body = await response.json();
      return body;
  } else {
      return null;
  }
}
