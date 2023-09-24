function gettime(){
		const date=new Date(document.getElementById('date').value);
		const show=document.getElementById('time').options;
		const hour=new Date().getHours()+2;
		for(let i=1;i<show.length;i++){
			const time=show[i].value;
			if(date.toDateString()==new Date().toDateString()&&time<hour){
				show[i].disabled=true;
			}
			else{
				show[i].disabled=false;
			}
		}
	}