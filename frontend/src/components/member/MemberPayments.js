import React, { useState, useEffect } from "react";
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
import { getAllPayments } from "../../services/MemberService";
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
  const [allPayments, setAllPayments] = useState([])
  const [memberPaymentsModal, setMemberPaymentsModal] = useState(false)


  useEffect(() => {
    getAllMembersPayments().then(res => {
      if (res) {
        setAllMembersPayments(res)
      }
    })
  }, [])

  useEffect(() => {
    getAllPayments().then(res => {
      if (res) {
        setAllPayments(res)
      }
    })
  }, [])

  function handleDeleteAllMemberPayments(){
    setMemberPaymentsModal(!memberPaymentsModal)
  }

  async function confirmDeleteAllMemberPayments(){
    axios.delete("https://member-payments-management.herokuapp.com/api/v1/payments");
    setMemberPaymentsModal(false)
  }

  function cancelDeleteAllMemberPayments(){
    setMemberPaymentsModal(!memberPaymentsModal)
  }
  return (
    <>

    {memberPaymentsModal ?         
    <Modal
          open={memberPaymentsModal}
          onClose={cancelDeleteAllMemberPayments}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >

          <Box sx={style}>
            <h2 id="modal-modal-title" variant="h6" component="h2">
              Delete all members payments
            </h2>
            <Typography id="modal-modal-description" sx={{ mt: 2 }}>
              Are you sure you want to delete all member payments?
            </Typography>
            <Button color="error" variant="outlined" component="label" onClick={cancelDeleteAllMemberPayments} sx={{ mt: 2 }}>Cancel</Button>
            <Button color="error" variant="contained" component="label" sx={{ ml: 2, mt: 2 }} onClick={confirmDeleteAllMemberPayments}>Delete all payments</Button>
          </Box>
        </Modal>: 
        null}
      <h2>Member payment management</h2>
      <p>
        Note: Because are using a free database we have limitation on how much data we can save.
        That is why you need to remove all existing payments data before you can upload a new Swedbank file
      </p>
      <FileUploader isAllowedToUpload={allPayments.length !== 0} />
      <Button color="error" variant="outlined" component="label" sx={{ mb: 5, mt: 2 }} disabled={allPayments.length === 0} onClick={handleDeleteAllMemberPayments}>
        Remove all existing member payments
      </Button>

      <TableContainer component={Paper}>
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