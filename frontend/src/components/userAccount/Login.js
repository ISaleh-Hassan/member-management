import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
// import "./Login.css";

import UserAccountService from "../../services/UserAccountService";
function Login() {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const login = (e) => {
    e.preventDefault();
    UserAccountService.loginAsync(userName, password);
    if (localStorage.getItem("jwtToken")) {
      navigate("member-payment-management");
    }
  };

  return (
    <>
      <div className="form">
        <form>
          <div className="input-container">
            <label>Användarnamn </label>
            <TextField id="outlined-start-adornmen"></TextField>
            <input 
              type="text"
              onChange={(e) => setUserName(e.target.value)}
              value={userName}
              required
            />
          </div>
          <div className="input-container">
            <label>Lösenord </label>
            <input
              type="password"
              onChange={(e) => setPassword(e.target.value)}
              value={password}
              required
            />
          </div>
          <div className="button-container">
            <input type="submit" value="Logga in" onClick={login} />
          </div>
        </form>
      </div>
    </>
  );
}

export default Login;
