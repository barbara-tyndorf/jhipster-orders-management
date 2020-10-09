import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IForwarder } from 'app/shared/model/forwarder.model';

@Component({
  selector: 'jhi-forwarder-detail',
  templateUrl: './forwarder-detail.component.html'
})
export class ForwarderDetailComponent implements OnInit {
  forwarder: IForwarder | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ forwarder }) => {
      this.forwarder = forwarder;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
