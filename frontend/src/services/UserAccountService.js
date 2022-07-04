import axios from "axios";
import { setGlobalState, useGlobalState } from "../state";

import React from "react";

const UserAccountService = {
  loginAsync: function (userName, password) {
    axios
      .get("http://localhost:8083/api/v1/user-accounts/login", {
        params: { userName: userName, password: password },
      })
      .then((res) => {
        console.log(res);
        if (res.status === 200 && res.data != null) {
          localStorage.setItem("jwtToken", res.data.jwtToken);
        } else {
          alert("fel användarnamn eller lösenord");
        }
      })
      .catch((err) => {
        console.log(err);
        return false;
      });
  },
};
export default UserAccountService;
