import { Component, OnInit } from '@angular/core';
import { IParlament } from '../parlament-interface';
import { ParlamentService } from '../parlament.service';

@Component({
  selector: 'app-list-parlaments',
  templateUrl: './list-parlaments.component.html',
  styleUrls: ['./list-parlaments.component.css'],
  providers: [ParlamentService]
})
export class ListParlamentsComponent implements OnInit {

  cards = [
    {title: 'Title 1', content: 'Content 1'},
    {title: 'Title 2', content: 'Content 2'},
    {title: 'Title 3', content: 'Content 3'},
    {title: 'Title 4', content: 'Content 4'}
  ];

  parlaments:IParlament[];
  error:string

  constructor(private parlamentService:ParlamentService) {
    this.parlaments=[];
    this.error="";
  }

  ngOnInit(): void {
    this.parlamentService.getParlaments()
      .subscribe(
        {
          next: (response) => {
            this.parlaments=response;
            console.log(this.parlaments)
          },
          error: (error) => this.error=error
        }
      )
  }

}
