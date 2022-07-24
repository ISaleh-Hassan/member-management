import axios from "axios";

const UserAccountService = {
  loginAsync: async function (userName, password) {
    axios
      .get(process.env.REACT_APP_BASE_SERVER_URL + "user-accounts/login", {
        params: { userName: userName, password: password },
      })
      .then((res) => {
        console.log(res);
        if (res.status === 200 && res.data != null) {
          sessionStorage.setItem("jwtToken", res.data.jwtToken);
        } else {
          alert("fel användarnamn eller lösenord");
        }
      })
      .catch((err) => {
        console.log(err);
        return false;
      });
  },
  logoutAsync: async function () {
    sessionStorage.removeItem("jwtToken");
  },

  registerAsync: async function (
    name,
    mobileNumber,
    email,
    username,
    password
  ) {
    const promise = axios.post(process.env.REACT_APP_BASE_SERVER_URL + 
      "user-accounts/register",
      null,
      {
        params: {
          name,
          mobileNumber,
          email,
          username,
          password,
        },
      }
    );

    const dataPromise = promise
      .then((res) => {
        console.log(res);
        if (
          res.status === 200 &&
          /*res.data.jwtToken != null &&*/
          res.data.userRegisteredSuccess
        ) {
          // sessionStorage.setItem("jwtToken", res.data.jwtToken);
        }
        return res.data;
      })
      .catch((err) => {
        console.log(err);
      });
    return dataPromise;
  },
};
export default UserAccountService;
