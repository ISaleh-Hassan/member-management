import React from "react";
import axios from "axios";

import "./App.css";
import Login from "./components/userAccount/Login";
import ProtectedRoute from "./routes/ProtectedRoute";
import Registration from "./components/userAccount/Registration";
import RegistrationInformation from "./components/userAccount/RegistrationInformation";
import MemberPayments from "./components/member/MemberPayments";
import MemberAdministration from "./components/member/MemberAdministration";
import UserAccountService from "./services/UserAccountService";
import { useGlobalState } from "./state";
import { Routes, Route, Link, Navigate } from "react-router-dom";
import Navbar from "./components/header/Navbar";

//This map is used to map the component name that will be shown in the Navbar with the actual route
var routeMap = {
  "Member payment management": "member-payment-management",
  "Member adminstration": "member-administration",
};

// All those mapping are not working yet. But they do exist in the navbar
// An exception is Logout function which is working fine.
const settings = ["Profile", "Account", "Dashboard", "Logout"];

function App() {
  const showRegistrationInformation = useGlobalState(
    "showRegistrationInformation"
  );
  const token = localStorage.getItem("jwtToken");
  const [isAuthorized, setIsAuthorized] = React.useState(false);
  console.log(isAuthorized);

  //TODO: I need to check how to make the endpoint call from here. The endpoint is working but not here

  React.useEffect(() => {
    if (token !== null && token !== undefined) {
      axios
        .post(
          process.env.REACT_APP_BASE_SERVER_URL +
            "user-accounts/validate-token?token=" +
            token
        )
        .then((res) => {
          setIsAuthorized(res.data);
        })
        .catch((err) => {
          console.error(err);
          setIsAuthorized(false);
        });
    } else {
      setIsAuthorized(false);
    }
  }, [token]);

  return (
    <div className="App">
      <Navbar
        routeMap={routeMap}
        settings={settings}
        isAuthorized={isAuthorized}
      />
      <Routes>
        <Route
          path="/"
          element={
            <ProtectedRoute isAuthorized={isAuthorized}>
              <MemberAdministration />
            </ProtectedRoute>
          }
        />
        <Route path="registration" element={<Registration />} />
        <Route
          path="member-payment-management"
          element={
            <ProtectedRoute isAuthorized={isAuthorized}>
              <MemberPayments />
            </ProtectedRoute>
          }
        />
        <Route
          path="member-administration"
          element={
            <ProtectedRoute isAuthorized={isAuthorized}>
              <MemberAdministration />
            </ProtectedRoute>
          }
        />
        <Route
          path="login"
          element={
            !isAuthorized ? (
              <Login />
            ) : (
              <Navigate to="/member-administration" replace />
            )
          }
        />
        <Route path="registration" element={<Registration />} />
        <Route
          path="registration-information"
          element={
            showRegistrationInformation[0] ? <RegistrationInformation /> : null
          }
        />
      </Routes>
    </div>
  );
}

export default App;
