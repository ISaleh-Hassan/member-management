import axios from "axios";

const UserAccountService = {
  loginAsync: async function (userName, password) {
    axios
      .get("https://member-payments-management.herokuapp.com/api/v1/user-accounts/login", {
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
    firstname,
    lastname,
    mobileNumber,
    email,
    username,
    password
  ) {
    const promise = axios.post(
      "https://member-payments-management.herokuapp.com/api/v1/user-accounts/register",
      null,
      {
        params: {
          firstname,
          lastname,
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
