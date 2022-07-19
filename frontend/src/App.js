import React from "react";
import "./App.css";
import Login from "./components/userAccount/Login";
import Registration from "./components/userAccount/Registration";
import RegistrationInformation from "./components/userAccount/RegistrationInformation";
import MemeberPayments from "./components/member/MemberPayments";
import MemberAdministration from "./components/member/MemberAdministration";
import UserAccountService from "./services/UserAccountService";
import { useGlobalState } from "./state";
import { Routes, Route, Link } from "react-router-dom";
import Navbar from "./components/header/Navbar";

//This map is used to map the component name that will be shown in the Navbar with the actual route
var routeMap = {
  "Member payment management":"member-payment-management",
  "Member adminstration": "member-administration"
}

// All those mapping are not working yet. But they do exist in the navbar
const settings = ['Profile', 'Account', 'Dashboard', 'Logout'];

function App() {
  const showRegistrationInformation = useGlobalState(
    "showRegistrationInformation"
  );
  return (
    <div className="App">
      <Navbar routeMap={routeMap} settings={settings}/>
          <ul>
              <li>
                <Link to="/">Login</Link>
              </li>
              <li>
                <Link to="/Registration">Register</Link>
              </li>
            </ul>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="registration" element={<Registration />} />
          <Route
            path="member-payment-management"
            element={<MemeberPayments />}
          />
           <Route
            path="member-administration"
            element={<MemberAdministration />}
          />
          <Route
            path="login"
            element={<Login />}
          />
          <Route
            path="registration"
            element={<Registration />}
          />
          {showRegistrationInformation[0] === true ? (
            <Route
              path="registration-information"
              element={<RegistrationInformation />}
            />
          ) : null}
        </Routes>
    </div>
  );
}

export default App;
