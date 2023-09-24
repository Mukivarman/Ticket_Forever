const getcontainer = document.querySelector('#movies .seat-container');
const selectedSeatsInput = document.getElementById('seat-select');
const countoftickets=document.getElementById('count');
const totalprice =document.getElementById('totalPrice');
const ticketprice=(document.getElementById('Price')).value;
const num_seat=parseInt(document.getElementById('tseat').value);
console.log(ticketprice);

  
 
  function createseat(){
    for(i=1;i<=num_seat;i++){
        const setseat=document.createElement('div');
        setseat.className="seat";
        setseat.textContent=i;
        setseat.setAttribute('data-seatnum',i)
        setseat.addEventListener('click', toggleSeat);
        getcontainer.appendChild(setseat);
    }
  }

function toggleSeat(event) {

    const seat = event.target;
    seat.classList.toggle('selected');
    updateSelectedSeats();
}

// Function to update the hidden input with selected seats
function updateSelectedSeats() {
    const selectedSeats = document.querySelectorAll('.selected');
    const selectedSeatNumbers = Array.from(selectedSeats).map(seat => seat.textContent);
    selectedSeatsInput.value = selectedSeatNumbers.join(',');
    countoftickets.value=selectedSeatNumbers.length;
    totalprice.value=selectedSeatNumbers.length*ticketprice;


}

  createseat();
 
  
  