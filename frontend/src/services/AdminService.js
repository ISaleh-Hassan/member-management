import axios from "axios";
export default async function loginAsync(userName, password) {
  axios
    .get("http://localhost:8083/api/v1/useraccounts/login", {
      params: { userName: userName, password: password },
    })
    .then((res) => {
      console.log(res);
      if(res.status === 200 && res.data === true){
        console.log("inloggad!")
      }
      else{
        console.log("fel användarnamn eller lösenord");
      }
    })
    .catch((err) => {
      console.log(err);
    });
}
