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

function EditUserAccount(props) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
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

  const updateUserAccount = (e) => {
    e.preventDefault();
    UserAccountService.updateUserAccount(email, password).then((res) => {
      setErrorMessage("")
    });
  };
  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <TextField
          type="text"
          label="email"
          id="outlined-start-adornmen"
          required
          onChange={(e) => setEmail(e.target.value)}
          value={email}
          helperText={email === "" ? "Empty field!" : " "}
        ></TextField>
      </Grid>
      <Grid item xs={12}>
        <TextField
          type={showPassword ? "text" : "password"}
          label="password"
          onChange={(e) => setPassword(e.target.value)}
          value={password}
          required
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
      {errorMessage ? (
        <Grid item xs={12}>
          <p style={{ color: "red" }}>{errorMessage}</p>
        </Grid>
      ) : null}
   
      <Grid item xs={12}>
        <Button
          variant="contained"
          type="submit"
          onClick={updateUserAccount}
          style={{ width: "200px" }}
        >
          Save
        </Button>
      </Grid>
    </Grid>
  );
}

export default EditUserAccount;
