import axios from "axios";
export default async function addPayments(selectedFile) {
    axios.post(process.env.REACT_APP_BASE_SERVER_URL + "payments", selectedFile, {
        headers:{
        "Content-Type":"multipart/form-data"
        }
    }).then(res => {
        return res
    })
    .catch((err) => {
      console.log(err);
    });
}

export default async function getAllPayments() {
    const response = await fetch(process.env.REACT_APP_BASE_SERVER_URL + "members/payments");
    if (response.status === 200) {
        let body = await response.json();
        return body;
    } else {
        return null;
    }
  }
