import React from "react";
import "./App.css";
import LoginIndex from "./components/admin/Login"
import MemeberPayments from "./components/member/MemberPayments";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link
} from "react-router-dom";
function App() {
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
              <Link to="/member-payment-management">Member payments</Link>
            </li>
          </ul>
        </nav>

        <Routes>
          <Route exact path="/" element={ <LoginIndex />} />
          <Route path="/member-payment-management" element={ <MemeberPayments />} />
        </Routes>
      </div>
    </Router>
    </div>
  );
}

export default App;
