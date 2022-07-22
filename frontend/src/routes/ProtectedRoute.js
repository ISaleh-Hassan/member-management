import React, { useContext } from "react";
import { Route, Navigate } from "react-router-dom";

  const ProtectedRoute = ({children }) => {
    const isAutherized = localStorage.getItem("jwtToken") !== null;
    if (!isAutherized) {
      return <Navigate to="/login" replace />;
    }
  
    return children;
  };


export default ProtectedRoute