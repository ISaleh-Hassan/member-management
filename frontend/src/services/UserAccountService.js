import axios from "axios";

const UserAccountService = {
  loginAsync: async function (userName, password) {
   const promise = axios
      .get(process.env.REACT_APP_BASE_SERVER_URL + "user-accounts/login", {
        params: { userName: userName, password: password },
      })
     const dataPromise = promise.then((res) => {
        if (res.status === 200 && res.data.jwtToken != null && res.data.isActivated) {
          localStorage.setItem("jwtToken", res.data.jwtToken);
          if(res.data.isAdmin){
            localStorage.setItem("isAdmin", true)
          }
        }
        return res.data;
      })
      .catch((err) => {
        console.log(err);
      });
      return dataPromise;
  },

  logoutAsync: async function () {
    localStorage.removeItem("jwtToken");
  },

  registerAsync: async function (
  registerUser
  ) {
    const promise = axios.post(process.env.REACT_APP_BASE_SERVER_URL + 
      "user-accounts/register",
      registerUser
    );
    const dataPromise = promise
      .then((res) => {
        if (
          res.status === 200 &&
          res.data.userRegisteredSuccess
        ) {
          // localStorage.setItem("jwtToken", res.data.jwtToken);
        }
        return res.data;
      })
      .catch((err) => {
        console.log(err);
      });
    return dataPromise;
  },

  updateAsync: async function (
    userAccount
    ) {
      const promise = axios.put(process.env.REACT_APP_BASE_SERVER_URL + 
        "user-accounts",
        userAccount
      );
      const dataPromise = promise
        .then((res) => {
          return res.data;
        })
        .catch((err) => {
          console.log(err);
        });
      return dataPromise;
    },

};
export default UserAccountService;
