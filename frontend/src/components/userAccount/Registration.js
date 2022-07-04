import * as React from "react";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";

export default function Registration() {
  return (
    <Box
      component="form"
      sx={{
        "& > :not(style)": { m: 1, width: "25ch" },
      }}
      noValidate
      autoComplete="off"
    >
      <TextField id="standard-basic" label="Standard" variant="standard" />
      <TextField
        id="standard-error-password-input"
        label="Password"
        type="password"
        autoComplete="current-password"
        variant="standard"
      />


<TextField
          error
          id="standard-error-helper-text"
          label="Error"
         
          helperText="Incorrect entry."
          variant="standard"
        />
    </Box>
  );
}
