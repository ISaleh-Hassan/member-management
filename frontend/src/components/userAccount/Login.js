import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";

import VisibilityIcon from "@mui/icons-material/Visibility";
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";
import InputAdornment from "@mui/material/InputAdornment";
import IconButton from "@mui/material/IconButton";
import Input from "@mui/material/Input";
import FilledInput from "@mui/material/FilledInput";
import OutlinedInput from "@mui/material/OutlinedInput";
import InputLabel from "@mui/material/InputLabel";
import UserAccountService from "../../services/UserAccountService";
import { Link } from "react-router-dom";

function Login(props) {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [invalidCredentials, setInvalidCredentials] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (props.isAuthorized) {
      navigate("/member-administration");
    }
  });
  const [showPassword, setShowPassword] = useState(false);
  // Password toggle handler
  const togglePassword = () => {
    setShowPassword(!showPassword);
  };
  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  const login = (e) => {
    e.preventDefault();
    UserAccountService.loginAsync(userName, password).then((res) => {
      if (res.jwtToken == null) {
        setInvalidCredentials(true);
      }
    });
  };
  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <TextField
          type="text"
          label="username"
          id="outlined-start-adornmen"
          required
          onChange={(e) => setUserName(e.target.value)}
          value={userName}
          error={userName === ""}
          helperText={userName === "" ? "Empty field!" : " "}
        ></TextField>
      </Grid>
      <Grid item xs={12}>
        <TextField
          type={showPassword ? "text" : "password"}
          label="password"
          onChange={(e) => setPassword(e.target.value)}
          value={password}
          required
          error={password === ""}
          helperText={password === "" ? "Empty field!" : " "}
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <IconButton
                  aria-label="toggle password visibility"
                  onClick={togglePassword}
                  edge="end"
                  onMouseDown={handleMouseDownPassword}
                >
                  {showPassword ? <VisibilityIcon /> : <VisibilityOffIcon />}
                </IconButton>
              </InputAdornment>
            ),
          }}
        ></TextField>
      </Grid>
      {invalidCredentials ? (
        <Grid item xs={12}>
          <p style={{ color: "red" }}>Wrong username or password!</p>
        </Grid>
      ) : null}
      <Grid item xs={12}>
        <div>
          Dont have an account? Register
          <Link to="/Registration"> Here</Link>
        </div>
      </Grid>
      <Grid item xs={12}>
        <Button
          variant="contained"
          type="submit"
          onClick={login}
          style={{ width: "200px" }}
        >
          Login
        </Button>
      </Grid>
    </Grid>
  );
}

export default Login;
