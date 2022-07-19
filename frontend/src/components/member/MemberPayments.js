import React, { useState, useEffect } from "react";
import Grid from "@mui/material/Grid";

import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import getAllMembersPayments from "../../services/MemberService";
import FileUploader from "../fileUploader/FileUploader";
import axios from "axios";



const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 600,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};

export default function MemeberPayments() {
  const [allMembersPayments, setAllMembersPayments] = useState([])
  const [transactionUploaderModal, setTransactionUploaderModal] = useState(false)


  useEffect(() => {
    getAllMembersPayments().then(res => {
      if (res) {
        setAllMembersPayments(res)
      }
    })
  }, [])

  async function confirmUploadNewTransactions(){
    axios.delete("https://member-payments-management.herokuapp.com/api/v1/payments");
    setTransactionUploaderModal(false)
  }

  function cancelUploadNewTransactions(){
    setTransactionUploaderModal(!transactionUploaderModal)
  }
  return (
    <>
      <h2>Member payment management</h2>
      <FileUploader />
      <TableContainer component={Paper} sx={{ paddingTop: 5 }}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>Name</TableCell>
              <TableCell>Mobile number</TableCell>
              <TableCell>Amount</TableCell>
              <TableCell>Transaction date</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {allMembersPayments.map((memberPayment, index) => (
              <TableRow
                key={index}
                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
              >
                <TableCell component="th" scope="row">
                  {memberPayment.memberName}
                </TableCell>
                <TableCell >{memberPayment.mobileNumber}</TableCell>
                <TableCell >{memberPayment.amount}</TableCell>
                <TableCell >{memberPayment.transactionDate}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  );
}