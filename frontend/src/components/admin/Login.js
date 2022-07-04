import React, { useState } from "react";
import "./Login.css";
import loginAsync from "../../services/AdminService";
function Login() {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");

  const login = (e) => {
    e.preventDefault();
    loginAsync(userName, password);
  };

  return (
    <>
      <div className="form">
        <form>
          <div className="input-container">
            <label>Användarnamn </label>
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
