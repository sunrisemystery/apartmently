import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserTile} from 'src/app/common/user-tile';
import {AuthenticationService} from 'src/app/services/authentication.service';
import {UserService} from 'src/app/services/user.service';


@Component({
  selector: 'app-user-tile',
  templateUrl: './user-tile.component.html',
  styleUrls: ['./user-tile.component.css']
})
export class UserTileComponent implements OnInit {

  @Input() userInfo: UserTile;

  constructor(private userService: UserService, private route: ActivatedRoute, private router: Router,
              private authService: AuthenticationService) {
  }

  ngOnInit(): void {
  }

  deleteUser(id: number): void {
    if (this.authService.currentUserValue.roles.includes('ADMIN')) {
      if (confirm('Are you sure that you want to delete this user?')) {
        this.userService.deleteUser(id).subscribe(
          response => {
            alert('User deleted successfully');
            this.router.navigateByUrl('/admin-panel');
          }
        );
      }
    }
  }

}
