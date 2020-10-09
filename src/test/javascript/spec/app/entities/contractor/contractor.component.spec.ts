import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OrdersManagementTestModule } from '../../../test.module';
import { ContractorComponent } from 'app/entities/contractor/contractor.component';
import { ContractorService } from 'app/entities/contractor/contractor.service';
import { Contractor } from 'app/shared/model/contractor.model';

describe('Component Tests', () => {
  describe('Contractor Management Component', () => {
    let comp: ContractorComponent;
    let fixture: ComponentFixture<ContractorComponent>;
    let service: ContractorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrdersManagementTestModule],
        declarations: [ContractorComponent],
        providers: []
      })
        .overrideTemplate(ContractorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContractorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContractorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Contractor(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.contractors && comp.contractors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
