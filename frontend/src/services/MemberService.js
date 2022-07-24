

export default async function getAllMembersPayments() {
  const response = await fetch(process.env.REACT_APP_BASE_SERVER_URL + "members/all-payments");
  if (response.status === 200) {
      let body = await response.json();
      return body;
  } else {
      return null;
  }
}

export async function getAllPayments() {
  const response = await fetch(process.env.REACT_APP_BASE_SERVER_URL + "payments");
  if (response.status === 200) {
      let body = await response.json();
      return body;
  } else {
      return null;
  }
}
