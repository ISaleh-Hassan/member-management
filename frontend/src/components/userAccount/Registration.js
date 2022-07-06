import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Visibility from "@mui/material/Icon";
import VisibilityOff from "@mui/material/Icon";
import InputAdornment from "@mui/material/InputAdornment";
import IconButton from "@mui/material/IconButton";

import UserAccountService from "../../services/UserAccountService";

export default function Registration() {
  const [firstname, setFirstname] = useState("");
  const [lastname, setLastname] = useState("");
  const [email, setEmail] = useState("");
  const [mobileNumber, setMobileNumber] = useState();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const navigate = useNavigate();

  const [mobileNumberErrorText, setMobileNumberErrorText] = useState("");
  const [emailErrorText, setEmailErrorText] = useState("");
  const [usernameErrorText, setUsernameErrorText] = useState("");

  const [passwordErrorText, setPasswordErrorText] = useState("");
  const [confirmPasswordErrorText, setConfirmPasswordErrorText] = useState("");

  const [values, setValues] = useState({
    amount: "",
    password: "",
    weight: "",
    weightRange: "",
    showPassword: false,
  });

  const handleClickShowPassword = () => {
    setValues({
      ...values,
      showPassword: !values.showPassword,
    });
  };

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };
  const register = (e) => {
    e.preventDefault();
    if (
      firstname.length > 0 &&
      lastname.length > 0 &&
      mobileNumber.length > 0 &&
      email.length > 0 &&
      username.length > 0 &&
      password.length > 0
    )
      if (password === confirmPassword) {
        {
          UserAccountService.registerAsync(
            firstname,
            lastname,
            "+46" + mobileNumber,
            email,
            username,
            password
          ).then((res) => {
            if (res.usernameExists) {
              setUsernameErrorText("username already exists");
            }
            if (res.emailExists) {
              setEmailErrorText("email already exists");
            }
          });
        }
      } else if (
        firstname.length === 0 ||
        lastname.length === 0 ||
        mobileNumber.length === 0 ||
        email.length === 0 ||
        username.length === 0 ||
        password.length === 0
      ) {
        alert("make sure you have filled in all fields");
      }
    //navigate("member-payment-management");
  };

  const validateMobileNumber = (e) => {
    var mobileRegex = new RegExp("^(7[0236])*([0-9]{4})*([0-9]{3})$");
    if (e === "") {
      setMobileNumberErrorText("Field is empty");
    } else if (mobileRegex.test(e)) {
      setMobileNumberErrorText("");
    } else if (!mobileRegex.test(e)) {
      setMobileNumberErrorText(
        "Mobile number must start with either: 70, 72, 73 or 76"
      );
    }
  };

  const validatePassword = (e) => {
    var passwordRegex = new RegExp("(?=.*[A-Za-z])(?=.*[0-9])[A-Za-zd]{8,}");
    if (e === "") {
      setPasswordErrorText("Field is empty");
    } else if (passwordRegex.test(e)) {
      setPasswordErrorText("");
    } else if (!passwordRegex.test(e)) {
      setPasswordErrorText(
        "Password must be at least 8 character long and contain at least one letter and one number"
      );
    }
  };

  const validateConfirmedPassword = (e) => {
    if (password !== e) {
      setConfirmPasswordErrorText("Password doesnt match!");
    }
    if (e.length === 0) {
      setConfirmPasswordErrorText("");
    }
  };
  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <TextField
          type="text"
          label="firstname"
          id="outlined-start-adornmen"
          required
          onChange={(e) =>
            setFirstname(
              e.target.value.charAt(0).toUpperCase() + e.target.value.slice(1)
            )
          }
          value={firstname}
          error={firstname === ""}
          helperText={firstname === "" ? "Empty field!" : " "}
        ></TextField>
      </Grid>
      <Grid item xs={12}>
        <TextField
          type="text"
          label="lastname"
          id="outlined-start-adornmen"
          required
          onChange={(e) =>
            setLastname(
              e.target.value.charAt(0).toUpperCase() + e.target.value.slice(1)
            )
          }
          value={lastname}
          error={lastname === ""}
          helperText={lastname === "" ? "Empty field!" : " "}
        ></TextField>
      </Grid>
      <Grid item xs={12}>
        <TextField style={{ width: "55px" }} readOnly value={"+46"}></TextField>
        <TextField
          onInput={(e) => {
            e.target.value = Math.max(0, parseInt(e.target.value))
              .toString()
              .slice(0, 9);
          }}
          type="number"
          label="mobile number"
          id="outlined-start-adornmen"
          required
          onChange={(e) => setMobileNumber(e.target.value)}
          onBlur={(e) => validateMobileNumber(e.target.value)}
          value={mobileNumber}
          error={mobileNumber === ""}
          helperText={mobileNumberErrorText}
        ></TextField>
      </Grid>
      <Grid item xs={12}>
        <TextField
          type="text"
          label="email"
          id="outlined-start-adornmen"
          required
          onChange={(e) => setEmail(e.target.value)}
          value={email}
          error={email === ""}
          helperText={emailErrorText}
        ></TextField>
      </Grid>
      <Grid item xs={12}>
        <TextField
          type="text"
          label="username"
          id="outlined-start-adornmen"
          required
          onChange={(e) => setUsername(e.target.value)}
          value={username}
          error={username === ""}
          helperText={usernameErrorText}
        ></TextField>
      </Grid>
      <Grid item xs={12}>
        <TextField
          inputProps={{ maxLength: 16 }}
          type="password"
          label="password"
          onChange={(e) => setPassword(e.target.value)}
          value={password}
          required
          error={password === ""}
          helperText={passwordErrorText}
          onBlur={(e) => validatePassword(e.target.value)}
          endadornment={
            <InputAdornment position="end">
              <IconButton
                aria-label="toggle password visibility"
                onClick={handleClickShowPassword}
                onMouseDown={handleMouseDownPassword}
                edge="end"
              >
                {values.showPassword ? <VisibilityOff /> : <Visibility />}
              </IconButton>
            </InputAdornment>
          }
        ></TextField>
        <Grid item xs={12}>
          <TextField
            inputProps={{ maxLength: 16 }}
            type="password"
            label="confirm password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
            error={confirmPassword === ""}
            helperText={confirmPasswordErrorText}
            onBlur={(e) => validateConfirmedPassword(e.target.value)}
            endadornment={
              <InputAdornment position="end">
                <IconButton
                  aria-label="toggle password visibility"
                  onClick={handleClickShowPassword}
                  onMouseDown={handleMouseDownPassword}
                  edge="end"
                >
                  {values.showPassword ? <VisibilityOff /> : <Visibility />}
                </IconButton>
              </InputAdornment>
            }
          ></TextField>
        </Grid>
      </Grid>
      <Grid item xs={12}>
        <Button
          variant="contained"
          type="submit"
          onClick={register}
          style={{ width: "200px" }}
        >
          Register
        </Button>
      </Grid>
    </Grid>
  );
}
