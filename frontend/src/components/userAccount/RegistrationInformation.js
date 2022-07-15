import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import Grid from "@mui/material/Grid";

export default function RegistrationInformation() {

  return (
    <Grid container spacing={2}>
      <Grid item xs={12}> 
     <div>An email have been sent to you asking for confirmation</div>
      </Grid>
    </Grid>
  );
}
