import React, { useContext } from "react";
import { Route, Navigate } from "react-router-dom";
import axios from "axios";

const ProtectedRoute = (props, { children }) => {
  //TODO: I need to check how to make the endpoint call from here. The endpoint is working but not here
  console.log(props.isAuthorized);
  if (!props.isAuthorized) {
    return <Navigate to="/login" replace />;
  }
  return children;
};

export default ProtectedRoute;
