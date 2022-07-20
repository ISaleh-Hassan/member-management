import React, { useState } from "react";
import omit from "lodash";
const useFormValidation = () => {
  //Form values
  const [inputValues, setInputValues] = useState({});
  //Errors
  const [errors, setErrors] = useState({});

  const validate = (event, name, value, password) => {
    //A function to validate each input values
    switch (name) {
      case "mobileNumber":
        var mobileRegex = new RegExp("^(7[02369])*([0-9]{4})*([0-9]{3})$");
        if (value === "") {
          setErrors({ ...errors, mobileNumber: "Field is empty" });
        } else if (mobileRegex.test(value)) {
          let newObj = omit(errors, "mobileNumber");
          setErrors(newObj);
        } else if (!mobileRegex.test(value)) {
          setErrors({
            ...errors,
            mobileNumber:
              "Mobile number must start with either: 70, 72, 73, 76 or 79",
          });
        }
        break;
      case "username":
        break;

      case "email":
        break;

      case "password":
        var passwordRegex = new RegExp(
          "(?=.*[A-Za-z])(?=.*[0-9])[A-Za-zd]{8,}"
        );
        if (value === "") {
          setErrors({ ...errors, password: "Field is empty" });
        } else if (passwordRegex.test(value)) {
          let newObj = omit(errors, "password");
          setErrors(newObj);
        } else if (!passwordRegex.test(value)) {
          setErrors({
            ...errors,
            password:
              "Password must be at least 8 character long and contain at least one letter and one number",
          });
        }
        break;

      case "confirmPassword":
        if (password !== value) {
          setErrors({ ...errors, confirmPassword: "Password doesnt match!" });
        }
        if (password.length === 0) {
          let newObj = omit(errors, "confirmPassword");
          setErrors(newObj);
        }
        break;
      default:
        break;
    }
  };

  //A method to handle form inputs
  const handleChange = (event, password) => {
    //To stop default events
    event.preventDefault();

    let name = event.target.name;
    let val = event.target.value;

    validate(event, name, val, password);

    //Let's set these values in state
    setInputValues({
      ...inputValues,
      [name]: val,
    });
  };

  return {
    inputValues,
    errors,
    handleChange,
  };
};

export default useFormValidation;
