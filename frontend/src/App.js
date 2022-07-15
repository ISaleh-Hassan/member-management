import React from "react";
import "./App.css";
import Login from "./components/userAccount/Login";
import Registration from "./components/userAccount/Registration";
import RegistrationInformation from "./components/userAccount/RegistrationInformation";
import MemeberPayments from "./components/member/MemberPayments";
import UserAccountService from "./services/UserAccountService";
import { useGlobalState } from "./state";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
function App() {
  const showRegistrationInformation = useGlobalState(
    "showRegistrationInformation"
  );
  return (
    <div className="App">
      <Router>
        <div>
          <nav>
            <ul>
              <li>
                <Link to="/">Login</Link>
              </li>
              <li>
                <Link to="/" onClick={UserAccountService.logoutAsync}>
                  Logout
                </Link>
              </li>
              <li>
                <Link to="/Registration">Register</Link>
              </li>
              <li>
                <Link to="/member-payment-management">Member payments</Link>
              </li>
            </ul>
          </nav>
          <Routes>
            <Route exact path="/" element={<Login />} />
            <Route exact path="/Registration" element={<Registration />} />
            <Route
              path="/member-payment-management"
              element={<MemeberPayments />}
            />
            {showRegistrationInformation[0] === true ? (
              <Route
                path="/registration-information"
                element={<RegistrationInformation />}
              />
            ) : null}
          </Routes>
          <></>
        </div>
      </Router>
    </div>
  );
}

export default App;
