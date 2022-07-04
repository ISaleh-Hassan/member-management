import axios from "axios";
export default async function addPayments(selectedFile) {
    axios.post("http://localhost:8083/api/v1/payments", selectedFile, {
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
    const response = await fetch("http://localhost:8083/api/v1/members/payments");
    if (response.status === 200) {
        let body = await response.json();
        return body;
    } else {
        return null;
    }
  }
