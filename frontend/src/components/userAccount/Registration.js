import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Visibility from "@mui/material/Icon";
import VisibilityOff from "@mui/material/Icon";
import InputAdornment from "@mui/material/InputAdornment";
import IconButton from "@mui/material/IconButton";
import { CircularProgress } from "@mui/material";
import { setGlobalState } from "../../state";
import UserAccountService from "../../services/UserAccountService";
import useFormValidation from "../../validation/useFormValidation";

export default function Registration() {
  const [firstname, setFirstname] = useState("");
  const [lastname, setLastname] = useState("");
  const [email, setEmail] = useState("");
  const [mobileNumber, setMobileNumber] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const [emailErrorText, setEmailErrorText] = useState("");
  const [usernameErrorText, setUsernameErrorText] = useState("");

  const [showProgress, setShowProgress] = useState(false);
  const { inputValues, errors, handleChange } = useFormValidation();

  console.log(errors);
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
      password.length > 0 &&
      password === confirmPassword
    ) {
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
        if (res.emailExist) {
          setEmailErrorText("email already exists");
        }
        if (res.mobileNumberExists) {
          errors.mobileNumber = "mobile number already exists";
          //setMobileNumberErrorText("mobile number already exists");
        }
        if (res.userRegisteredSuccess) {
          setShowProgress(true);
          setGlobalState("showRegistrationInformation", true);
        }
      });
    } else if (
      firstname.length === 0 ||
      lastname.length === 0 ||
      mobileNumber.length === 0 ||
      email.length === 0 ||
      username.length === 0 ||
      password.length === 0
    ) {
      alert("Make sure you have filled in all fields");
    }
    //navigate("member-payment-management");
  };

  return (
    <Grid container
          rowSpacing={2}
    >
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
        <>
        <TextField style={{ width: "55px" }} readOnly value={"+46"}></TextField>
        <TextField
          onInput={(e) => {
            e.target.value = Math.max(0, parseInt(e.target.value))
              .toString()
              .slice(0, 9);
          }}
          name="mobileNumber"
          type="number"
          label="mobile number"
          id="outlined-start-adornmen"
          required
          onChange={(e) => setMobileNumber(e.target.value)}
          onBlur={(e) => handleChange(e)}
          value={mobileNumber}
          error={mobileNumber === ""}
          helperText={errors.mobileNumber}
        ></TextField>
        </>
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
          name="password"
          inputProps={{ maxLength: 16 }}
          type="password"
          label="password"
          onChange={(e) => setPassword(e.target.value)}
          value={password}
          required
          error={password === ""}
          helperText={errors.password}
          onBlur={(e) => handleChange(e)}
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
      <Grid item xs={12}>
        <TextField
            name="confirmPassword"
            inputProps={{ maxLength: 16 }}
            type="password"
            label="confirm password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
            error={confirmPassword === ""}
            helperText={errors.confirmPassword}
            onBlur={(e) => handleChange(e, password)}
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
          >
          </TextField>
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
      <Grid item xs={12}>
        {showProgress ? <CircularProgress /> : null}
      </Grid>
    </Grid>
  );
}
