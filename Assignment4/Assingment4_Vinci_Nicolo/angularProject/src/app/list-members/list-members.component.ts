import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IMember } from '../models/member-interface';
import { ParliamentService } from '../services/parliament.service';

@Component({
  selector: 'app-list-members',
  templateUrl: './list-members.component.html',
  styleUrls: ['./list-members.component.css'],
  providers: [ParliamentService]
})
export class ListMembersComponent implements OnInit {

  private _members:IMember[];

  constructor(private parliamentService:ParliamentService, private router:Router) {
    this._members=[];
  }

  ngOnInit(): void {
    this.parliamentService.getMembers()
      .subscribe(
        {
          next: (members) => {
            this._members=members;
            this._members.sort((a:IMember,b:IMember)=>a.ParliamentaryName.localeCompare(b.ParliamentaryName));
          },
          error: (error) => {
            console.log(error);
          }
        }
    )


  }

  public cardClicked(id:number) {
    this.router.navigate(['cardMember', id]);
  }

  public get members(): IMember[] {
    return this._members;
  }

}
